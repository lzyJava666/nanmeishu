package com.nanmeishu.user.service;

import com.nanmeishu.user.entity.User;

import java.util.List;

public interface FriendService {
    /**
     * 通过手机号或者昵称 查找用户列表，且不返回已成为好友关系的用户
     * @param content 通过手机号或者昵称
     * @param  userId 查找人userId
     * @return 用户列表
     */
    List<User> listUserByPhoneOrName(String content,String userId);
}