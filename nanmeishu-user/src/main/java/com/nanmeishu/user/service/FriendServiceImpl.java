package com.nanmeishu.user.service;

import com.nanmeishu.user.entity.Friend;
import com.nanmeishu.user.entity.User;
import com.nanmeishu.user.mapper.FriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("friendService")
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendMapper friendMapper;


    @Override
    public List<User> listUserByPhoneOrName(String content, String userId) {

        return friendMapper.listUserByPhoneOrName(content,userId);
    }

    @Override
    public List<Friend> listFriendByMe(String userId) {
        return friendMapper.listFriendByMe(userId);
    }
}
