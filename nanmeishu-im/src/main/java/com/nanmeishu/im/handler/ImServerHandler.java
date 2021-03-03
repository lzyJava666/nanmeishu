package com.nanmeishu.im.handler;

import com.alibaba.fastjson.JSON;
import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.im.entity.MessageCode;
import com.nanmeishu.im.entity.MessageProtocol;
import com.nanmeishu.im.entity.UserChannel;
import com.nanmeishu.im.feign.UserFeign;
import com.nanmeishu.im.util.RedisUtil;
import com.nanmeishu.util.JwtUtil;
import com.nanmeishu.util.ResultUtil;
import com.nanmeishu.util.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 存放登录用户信息 格式为：userId，userChannel类信息，是否在线
     */
    public static Map<String, Object[]> userChannelTable = new ConcurrentHashMap<>();

    //存放连接用户的组
    public static ChannelGroup channelGroup = new DefaultChannelGroup("ChannelGroups", GlobalEventExecutor.INSTANCE);

    RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);

    /**
     * 读取消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器收到消息：" + msg.text());
        System.out.println("id:" + ctx.channel().id().asLongText());
        String res = msg.text();
        MessageProtocol messageProtocol = JSON.parseObject(res, MessageProtocol.class);
        switch (messageProtocol.getType()) {
            case MessageCode.LOGIN: {
                //登录
                toLogin(messageProtocol, ctx);
            }
            break;
            case MessageCode.EXIT_LOGIN: {
                // 退出登录
                toExit(messageProtocol, ctx);
            }
            break;
            case MessageCode.USER_ADD: {
                //添加好友
                toUserAdd(messageProtocol, ctx);
            }
            break;
            case MessageCode.RETURN_CHAT_ADD: {
                //处理添加好友请求消息
                disposeAddUser(messageProtocol, ctx);
            }
            break;
            case MessageCode.USER_CHAT: {
                //处理私聊消息请求
                toUserChat(messageProtocol, ctx);
            }
            break;
            case MessageCode.TAKE_USER_CHAT: {
                //目标用户查看私聊消息请求
                toTakeUserChat(messageProtocol, ctx);
            }
            break;
        }
    }

    //处理目标用户查看消息请求
    private void toTakeUserChat(MessageProtocol messageProtocol, ChannelHandlerContext ctx) {
        String token = messageProtocol.getToken();
        String userId = JwtUtil.get(token, "userId");
        Jedis jedis = redisUtil.getJedis();
        try {
            String s = jedis.get(userId + ":" + messageProtocol.getFromId());
            List<MessageProtocol> messageProtocols = JSON.parseArray(s, MessageProtocol.class);
            for (MessageProtocol protocol : messageProtocols) {
                protocol.setIsShow(1);
                protocol.setIsSuccess(1);
                protocol.setReadTime(LocalDateTime.now());
            }
            jedis.set(userId + ":" + messageProtocol.getFromId(), JSON.toJSONString(messageProtocols));
            //jedis.set(messageProtocol.getFromId()+":"+userId,JSON.toJSONString(messageProtocols));
        } finally {
            jedis.close();
        }
    }

    //处理私聊请求
    private void toUserChat(MessageProtocol messageProtocol, ChannelHandlerContext ctx) {
        if (isExitUser(messageProtocol.getFromId())) {
            //目标用户在线
            Object[] userObj = userChannelTable.get(messageProtocol.getFromId().toString());
            UserChannel fromUser = (UserChannel) userObj[0];
            System.out.println(fromUser);
            //拿到目标用户的channel
            Channel fromChannel = fromUser.getChannel();
            //推送消息
            messageProtocol.setIsSuccess(1);
            messageProtocol.setUserId(Long.parseLong(JwtUtil.get(messageProtocol.getToken(), "userId")));
            fromChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageProtocol)));
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageProtocol)));
        } else {
            //目标用户离线
        }
        //处理完毕 存入redis
        Jedis jedis = redisUtil.getJedis();
        try {
            //拿到二人的消息列表
            if (messageProtocol.getUserId() == null) {
                messageProtocol.setUserId(Long.parseLong(JwtUtil.get(messageProtocol.getToken(), "userId")));
            }
            String fromToUser = jedis.get(messageProtocol.getFromId() + ":" + messageProtocol.getUserId());
            List<MessageProtocol> fromToUserMessage = JSON.parseArray(fromToUser, MessageProtocol.class);
            fromToUserMessage.add(messageProtocol);
            String set = jedis.set(messageProtocol.getFromId() + ":" + messageProtocol.getUserId(), JSON.toJSONString(fromToUserMessage));
            System.out.println(jedis.lastsave());
            System.out.println(messageProtocol.getFromId() + ":" + messageProtocol.getUserId() + "  " + set);
            System.out.println(jedis.get(messageProtocol.getFromId() + ":" + messageProtocol.getUserId()));
            //第二人的缓存也要添加数据
            List<MessageProtocol> myUserMessage = JSON.parseArray(jedis.get(messageProtocol.getUserId() + ":" + messageProtocol.getFromId()), MessageProtocol.class);
            myUserMessage.add(messageProtocol);
            String set1 = jedis.set(messageProtocol.getUserId() + ":" + messageProtocol.getFromId(), JSON.toJSONString(myUserMessage));
            System.out.println(messageProtocol.getUserId() + ":" + messageProtocol.getFromId() + " " + set1);
            System.out.println(jedis.get(messageProtocol.getUserId() + ":" + messageProtocol.getFromId()));
        } finally {
            jedis.close();
        }

    }

    //处理添加好友请求消息
    private void disposeAddUser(MessageProtocol messageProtocol, ChannelHandlerContext ctx) {
        //RedisUtil redisUtils = SpringUtil.getBean(RedisUtil.class);
        Jedis jedis = redisUtil.getJedis();
        String token = messageProtocol.getToken();
        String userId = JwtUtil.get(token, "userId");
        String friendUserId = messageProtocol.getFromId().toString();
        try {
            //1.将redis中的未处理标志改为具体处理标志,注：双方信息都要
            List<MessageProtocol> messageProtocols = JSON.parseArray(jedis.get(userId + ":" + friendUserId), MessageProtocol.class);
            System.out.println(messageProtocols);
            for (MessageProtocol protocol : messageProtocols) {
                if (protocol.getType() == 114) {
                    //添加好友请求
                    Map content = JSON.parseObject(protocol.getContent(), Map.class);
                    if (JSON.parseObject(messageProtocol.getContent(), Map.class).get("status").toString().equals("1")) {
                        //同意好友请求
                        content.put("isAddStatus", 1);
                        //数据库添加好友
                        UserFeign friendController = SpringUtil.getBean(UserFeign.class);
                        friendController.insert(JSON.toJSONString(content));
                    } else {
                        //拒绝好友请求
                        content.put("isAddStatus", 2);
                    }
                    protocol.setContent(JSON.toJSONString(content));
                    jedis.set(userId + ":" + friendUserId, JSON.toJSONString(messageProtocols));
                    jedis.set(friendUserId + ":" + userId, JSON.toJSONString(messageProtocols));
                    //推送给用户消息
                    charToAddUser(friendUserId);
                }
            }
        } finally {
            jedis.close();
        }
    }

    //推送消息给目标用户  ---关于好友申请
    private void charToAddUser(String friendUserId) {
        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setType(1141);
        messageProtocol.setCreateTime(LocalDateTime.now());
        Object[] objects = userChannelTable.get(friendUserId);
        if (objects == null) {
            return;
        }
        Boolean object = (Boolean) objects[1];
        if (object) {
            //在线 推送给用户
            UserChannel userChannel = (UserChannel) objects[0];
            userChannel.getChannel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageProtocol)));
        } else {
            //离线
        }
    }

    //添加好友处理
    private void toUserAdd(MessageProtocol messageProtocol, ChannelHandlerContext ctx) {
        Map map = JSON.parseObject(messageProtocol.getContent(), Map.class);
        //判断用户是否在线
        boolean exitUser = isExitUser(messageProtocol.getFromId());
        String userId = JwtUtil.get(messageProtocol.getToken(), "userId");
        map.put("myUserId", userId);
        messageProtocol.setContent(JSON.toJSONString(map));
        messageProtocol.setUserId(Long.parseLong(userId));
        //RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        Jedis jedis = redisUtil.getJedis();
        if (exitUser) {
            //用户在线 推送消息
            messageProtocol.setIsSuccess(1);
            //获取用户通道信息
            Object[] userObj = userChannelTable.get(messageProtocol.getFromId().toString());
            UserChannel userChannel = (UserChannel) userObj[0];
            Channel channel = userChannel.getChannel();
            System.out.println("对方用户的channel：" + channel.remoteAddress());
            System.out.println("本人用户的channel" + ctx.channel().remoteAddress());
            //推送给目标用户
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageProtocol)));
        }
        // 记录存入redis中，存二份 user1:user2  user2:user1
        List<MessageProtocol> messageProtocols = new ArrayList<>();
        messageProtocols.add(messageProtocol);
        jedis.set(userId + ":" + messageProtocol.getFromId(), JSON.toJSONString(messageProtocols));
        jedis.set(messageProtocol.getFromId() + ":" + userId, JSON.toJSONString(messageProtocols));
        jedis.close();
    }

    //指定用户是否在线
    private boolean isExitUser(Long userId) {
        Object[] userObj = userChannelTable.get(userId.toString());
        if (userObj == null) {
            return false;
        }
        boolean isExit = true;
        if (isExit) {
            //用户在线
            return true;
        } else {
            return false;
        }
    }

    //退出登录
    private void toExit(MessageProtocol messageProtocol, ChannelHandlerContext ctx) {
        String token = JSON.parseObject(messageProtocol.getContent(), Map.class).get("token").toString();
        String userId = JwtUtil.get(token, "userId");
        Object[] objects = userChannelTable.get(userId);
        UserChannel userChannel = (UserChannel) objects[0];
        userChannel.setCreateTime(null);
        userChannel.setEndTime(LocalDateTime.now());
        objects[0] = userChannel;
        objects[1] = false;
        userChannelTable.put(userId, objects);
        System.out.println("用户：" + token + "已经断开了连接\n当前人数为：" + count());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("true"));
    }

    //登录操作
    private void toLogin(MessageProtocol messageProtocol, ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        UserFeign userFeign = SpringUtil.getBean(UserFeign.class);
        System.out.println("传入内容：" + messageProtocol.getContent());
        //获取登录信息
        ResponseResult login = userFeign.login(JSON.parseObject(messageProtocol.getContent(), Map.class));
        if (login.getErrcode() == 200) {
            String token = login.getData().toString();
            String userId = JwtUtil.get(token, "userId");
            //登录成功
            //判断是否为已登陆过的用户
            if (userChannelTable.get(userId) == null) {
                //用户从未登录过,将用户添加到userChannelTable中，
                Object[] objects = new Object[2];
                UserChannel newUserChannel = new UserChannel();
                newUserChannel.setChannel(channel);
                newUserChannel.setEndTime(null);
                newUserChannel.setCreateTime(LocalDateTime.now());
                newUserChannel.setToken(token);
                objects[0] = newUserChannel;
                objects[1] = true;
                userChannelTable.put(userId, objects);
            } else {
                //用户已登录过服务器
                Object[] objects = userChannelTable.get(userId);
                //判断用户是否在线
                if ((Boolean) objects[1]) {
                    //用户在线，强制退出在线账号
                    exitCurrentLogin(objects);
                }
                //上线处理
                loginFlag(userId, channel, token);
                //离线消息推送
                offLineCharPush(userId);
            }
            MessageProtocol messageProtocol1 = new MessageProtocol();
            messageProtocol.setType(MessageCode.LOGIN);
            messageProtocol.setContent(token);
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(messageProtocol1)));
        } else {
            //登录失败
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(ResultUtil.error("账号或密码出错"))));
        }
    }

    //被强制下线处理
    private void exitCurrentLogin(Object[] objects) {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常：" + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }

    //连接上服务器
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //统计在线人数
        channelGroup.add(channel);
        System.out.println("------连接-----" + ctx.channel().id().asLongText());
        System.out.println("当前连接人数：" + count());
    }

    //将连接人员标记为上线
    private void loginFlag(String userId, Channel channel, String token) {
        Object[] currentObject = userChannelTable.get(userId);
        UserChannel userChannel = (UserChannel) currentObject[0];
        userChannel.setCreateTime(LocalDateTime.now());
        userChannel.setEndTime(null);
        userChannel.setChannel(channel);
        userChannel.setToken(token);
        currentObject[0] = userChannel;
        currentObject[1] = true;
        userChannelTable.put(userId, currentObject);
    }

    //离线消息推送
    private void offLineCharPush(String token) {
    }

    //统计在线人数
    private int count() {
        return channelGroup.size();
    }

    /**
     * 断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将断开成员标记为离线,并更改用户状态信息
        exitLoginFlag(channel);
        System.out.println("当前服务器成员人数：" + count());
    }

    //将断开成员标记为离线,并更改用户状态信息
    private void exitLoginFlag(Channel channel) {
        userChannelTable.forEach((key, value) -> {
            Object[] objects = (Object[]) value;
            UserChannel userChannel = (UserChannel) objects[0];
            if (channel == userChannel.getChannel()) {
                userChannel.setEndTime(LocalDateTime.now());
                userChannel.setCreateTime(null);
                objects[1] = false;
                objects[0] = userChannel;
                userChannelTable.put(key, objects);
                return;
            }
        });
    }
}
