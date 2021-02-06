package com.nanmeishu.im.handler;

import com.alibaba.fastjson.JSON;
import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.im.entity.MessageProtocol;
import com.nanmeishu.im.entity.UserChannel;
import com.nanmeishu.im.feign.UserFeign;
import com.nanmeishu.util.ResultUtil;
import com.nanmeishu.util.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.tomcat.jni.Local;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //存放游客信息
    public static List<UserChannel> userChannels = new ArrayList<>();
    //存放登录用户信息
    public static Map<String,Object> userChannelMap=new ConcurrentHashMap<String,Object>();


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("服务器收到消息：" + msg.text());
        System.out.println("id:" + ctx.channel().id().asLongText());
        String res = msg.text();
        MessageProtocol messageProtocol = JSON.parseObject(res, MessageProtocol.class);
        switch (messageProtocol.getType()){
            case 1:{
                //登录
                toLogin(messageProtocol,ctx);
            }
            break;
            case 0:{
                // 退出登录
                toExit(messageProtocol,ctx);
            }
            break;
        }



    }

    //退出登录
    private void toExit(MessageProtocol messageProtocol, ChannelHandlerContext ctx) {
        String token = messageProtocol.getContent().get("token").toString();
        for (UserChannel userChannel : userChannels) {
            if(userChannel.getToken().equals(token)){
                userChannel.setCreateTime(null);
                userChannel.setEndTime(LocalDateTime.now());
            }
        }
        System.out.println("用户："+token+"已经断开了连接\n当前人数为："+count());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("true"));
    }

    //登录操作
    private void toLogin(MessageProtocol messageProtocol,ChannelHandlerContext ctx){
        UserFeign userFeign = SpringUtil.getBean(UserFeign.class);
        System.out.println("传入内容：" + messageProtocol.getContent());
        //获取登录信息
        ResponseResult login = userFeign.login(messageProtocol.getContent());
        if (login.getErrcode() == 200) {
            String token=login.getData().toString();
            //登录成功
            boolean flag=false; //标记，找到相同id时会变true
            for (UserChannel userChannel : userChannels) {
                if (ctx.channel().id().asLongText().equals(userChannel.getChannelId())&&(!userChannel.getToken().equals(token))) {
                    //找到通过通道id，且
                    flag=true;
                    userChannel.setToken(login.getData().toString());
                    userChannel.setCreateTime(LocalDateTime.now());
                    userChannel.setEndTime(null);
                    break;
                }
            }
            if(!flag){
                //服务器上找不到系统channel id
                UserChannel myUser=new UserChannel();
                myUser.setToken(login.getData().toString());
                myUser.setCreateTime(LocalDateTime.now());
                myUser.setEndTime(null);
                myUser.setChannelId(ctx.channel().id().asLongText());
                myUser.setChannel(ctx.channel());
                userChannels.add(myUser);
            }
            System.out.println(login.getData().toString());
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(ResultUtil.success(token))));
        } else {
            //登录失败
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(ResultUtil.error("账号或密码出错"))));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常：" + cause.getMessage());
        ctx.close();
    }

    //连接上服务器
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        UserChannel userChannel = new UserChannel();
        userChannel.setChannelId(channel.id().asLongText());
        userChannel.setChannel(channel);
        userChannel.setToken("-1");
        userChannel.setCreateTime(LocalDateTime.now());
        userChannels.add(userChannel);
        System.out.println("------连接-----" + ctx.channel().id().asLongText());
        System.out.println("当前连接游客人数："+count());
    }

    //统计在线人数
    private int count(){
        int c=0;
        for (UserChannel userChannel : userChannels) {
            if(userChannel.getCreateTime()!=null){
               c++;
            }
        }
        return c;
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
        UserChannel u = null;
        if (userChannels == null || userChannels.size() == 0) {
            return;
        }
        for (UserChannel userChannel : userChannels) {
            if (userChannel.getChannelId().equals(channel.id().asLongText())) {
                u = userChannel;
                if (userChannel.getToken().equals("-1")) {
                    //游客 直接移除出数组
                    userChannels.remove(userChannel);
                } else {
                    userChannel.setEndTime(LocalDateTime.now());
                    userChannel.setCreateTime(null);
                }
                break;
            }
        }
        System.out.println(u);
        System.out.println("[成员：]" + (u.getToken().equals("-1") ? "游客" : u.getToken()) + " 断开连接");
        System.out.println("当前服务器成员人数：" + count());
    }
}
