package com.nanmeishu.user.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nanmeishu.user.entity.Friend;
import com.nanmeishu.user.entity.User;
import com.nanmeishu.user.mapper.FriendMapper;
import com.nanmeishu.user.mapper.UserMapper;
import com.nanmeishu.user.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.*;

@Service("friendService")
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendMapper friendMapper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserMapper userMapper;


    @Override
    public List<User> listUserByPhoneOrName(String content, String userId) {

        return friendMapper.listUserByPhoneOrName(content,userId);
    }

    @Override
    public List<Friend> listFriendByMe(String userId) {
        return friendMapper.listFriendByMe(userId);
    }

    @Override
    public List<Map> listAddFriend(String userId) {
        Jedis jedis = redisUtil.getJedis();
        List<Map> objects=new ArrayList<>();
        try {
            List<Object> messageProtocols;
            String s;
            Map obj;
            Set<String> keys = jedis.keys(userId + "*");
            for (String key : keys) {
                obj=new HashMap();
                s = jedis.get(key);
                messageProtocols= JSON.parseArray(s,Object.class);
                System.out.println(messageProtocols);
                if(messageProtocols.size()==1){
                    //一条信息，代表的是好友申请
                    Map<String,Object> map=(Map) messageProtocols.get(0);
                    obj.put("createTime",map.get("createTime"));
                    obj.put("user",userMapper.selectById(map.get("userId").toString()));
                    obj.put("friend",map.get("content"));
                    obj.put("message",JSON.parseObject(map.get("content").toString(),Map.class).get("message"));
                    objects.add(obj);
                }
            }
            System.out.println("objects:"+objects);
        }finally {
            jedis.close();
        }
        return objects;
    }

    @Override
    public void flagAddFriend(String userId) {
        Jedis jedis = redisUtil.getJedis();
        try {
            Set<String> keys = jedis.keys(userId + "*");
            List<Map> messageProtocols;
            List<Map> newMessageProtocols;
            String s;
            String you;
            Map obj;
            for (String key : keys) {
                newMessageProtocols=new ArrayList<>();
                s = jedis.get(key);
                messageProtocols=JSON.parseArray(s,Map.class);
                if(messageProtocols.size()==1){
                    //只有一个说明为添加请求
                    obj = messageProtocols.get(0);
                    obj.put("isShow",1);
                    obj.put("readTime", LocalDateTime.now());
                    newMessageProtocols.add(obj);
                    jedis.set(key,JSON.toJSONString(newMessageProtocols));
                    //更改对方状态
                    you=key.substring(key.indexOf(":")+1)+":"+key.substring(0,key.indexOf(":"));
                    System.out.println(you);
                    jedis.set(you,JSON.toJSONString(newMessageProtocols));
                }
            }
        }finally {
            jedis.close();
        }
    }

    @Override
    public List<Map> listNoSuccessAddFriend(String userId) {
        Jedis jedis = redisUtil.getJedis();
        List<Map> maps=new ArrayList<>();
        String s;
        List<Map> messageProtocols;
        Map currentMap;
        Map obj;
        try {
            Set<String> keys = jedis.keys(userId + "*");
            for (String key : keys) {
                s = jedis.get(key);
                messageProtocols=JSON.parseArray(s,Map.class);
                if(messageProtocols.size()==1){
                    currentMap = messageProtocols.get(0);
                    Map content = JSON.parseObject(currentMap.get("content").toString(), Map.class);
                    if((currentMap.get("userId").toString().equals(userId)&&
                    !JSON.parseObject(currentMap.get("content").toString(),Map.class).get("isAddStatus").toString()
                        .equals("0"))||(!currentMap.get("userId").toString().equals(userId))){
                         //未处理的好友申请,并且不是本人发起的好友请求
                         obj=new HashMap();
                         obj.put("createTime",currentMap.get("createTime"));
                         if(!currentMap.get("userId").toString().equals(userId)){
                             //不是本人
                             obj.put("user",userMapper.selectById(currentMap.get("userId").toString()));
                         }else{
                             obj.put("user",userMapper.selectById(currentMap.get("fromId").toString()));
                         }

                         obj.put("friend",JSON.parseObject(currentMap.get("content").toString(),Map.class));
                         obj.put("message",JSON.parseObject(currentMap.get("content").toString(),Map.class).get("message"));
                         maps.add(obj);
                     }
                }
            }
        }finally {
            jedis.close();
        }
        return maps;
    }

    @Override
    public void insert(Friend friend) {
        int insert = friendMapper.insert(friend);
        if(insert<=0){
            throw new RuntimeException("新增出错");
        }
    }

    @Override
    public int countFriendByUserId(String userId) {
        Integer countFrient=0;
        countFrient = friendMapper.selectCount(new QueryWrapper<Friend>().eq("my_user_id", userId));
        return countFrient;
    }

    @Override
    public List<Map> listChatByFromUser(String userId, String fromId) {
        Jedis jedis = redisUtil.getJedis();
        List<Map> messageMaps=null;
        List<Map> maps=new ArrayList<>();
        try {
            String s = jedis.get(userId + ":" + fromId);
            messageMaps = JSON.parseArray(s, Map.class);
            User myUser=userMapper.selectById(userId);
            User fromUser=userMapper.selectById(fromId);
            System.out.println("聊天记录："+messageMaps);
            if(messageMaps==null){
                return messageMaps;
            }
            for (Map messageMap : messageMaps) {
                if(!messageMap.get("type").toString().equals("11")){
                    continue;
                }
                messageMap.put("myUser",myUser);
                messageMap.put("fromUser",fromUser);
                maps.add(messageMap);
            }
        }finally {
            jedis.close();
        }
        return maps;
    }

    @Override
    public List<Map> listChatByToken(String userId) {
        Jedis jedis = redisUtil.getJedis();
        List<Map> maps=new ArrayList<>();
        String brName="";
        try {
            //获取和当前用户有交集的所有聊天列表
            Set<String> keys = jedis.keys(userId + "*");
            String s;
            List<Map> maps1;
            User fromUser;
            String friendId;
            int noSize=0;
            for (String key : keys) {
                s = jedis.get(key);
                maps1 = JSON.parseArray(s, Map.class);
                if(maps1.size()<=1){
                    continue;
                }
                for (Map map : maps1) {
                    if(map.get("isShow").toString().equals("0")){
                        //未读
                        noSize++;
                    }
                }
                friendId=maps1.get(maps1.size()-1).get("fromId").toString().equals(userId)?
                        maps1.get(maps1.size()-1).get("userId").toString() :
                        maps1.get(maps1.size()-1).get("fromId").toString();
                brName=friendMapper.selectOne(new QueryWrapper<Friend>()
                        .eq("user_id",friendId)).getBrName();

                fromUser=userMapper.selectById(friendId);
                maps1.get(maps1.size()-1).put("friendUser",fromUser);
                maps1.get(maps1.size()-1).put("noSize",noSize);
                maps1.get(maps1.size()-1).put("brName",brName);
                maps.add(maps1.get(maps1.size()-1));
            }
        }finally {
            jedis.close();
        }

        return maps;
    }

}
