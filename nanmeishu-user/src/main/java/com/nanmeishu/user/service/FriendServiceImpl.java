package com.nanmeishu.user.service;

import com.alibaba.fastjson.JSON;
import com.nanmeishu.user.entity.Friend;
import com.nanmeishu.user.entity.User;
import com.nanmeishu.user.mapper.FriendMapper;
import com.nanmeishu.user.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("friendService")
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendMapper friendMapper;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public List<User> listUserByPhoneOrName(String content, String userId) {

        return friendMapper.listUserByPhoneOrName(content,userId);
    }

    @Override
    public List<Friend> listFriendByMe(String userId) {
        return friendMapper.listFriendByMe(userId);
    }

    @Override
    public List<User> listAddFriend(String userId) {
        Jedis jedis = redisUtil.getJedis();
        try {
            Set<String> keys = jedis.keys(userId + "*");
            for (String key : keys) {
                String s = jedis.get(key);
                List<Object> messageProtocols= JSON.parseArray(s,Object.class);
                System.out.println(messageProtocols);

            }
            System.out.println(keys);
        }finally {
            jedis.close();
        }
        return null;
    }
}
