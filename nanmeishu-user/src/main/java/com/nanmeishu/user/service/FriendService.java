package com.nanmeishu.user.service;

import com.nanmeishu.user.entity.Friend;
import com.nanmeishu.user.entity.User;

import java.util.List;
import java.util.Map;

public interface FriendService {
    /**
     * 通过手机号或者昵称 查找用户列表，且不返回已成为好友关系的用户
     * @param content 通过手机号或者昵称
     * @param  userId 查找人userId
     * @return 用户列表
     */
    List<User> listUserByPhoneOrName(String content,String userId);

    /**
     * 获取当前用户的好友列表
     * @param userId 当前用户
     * @return
     */
    List<Friend> listFriendByMe(String userId);

    /**
     * 获取当前用户的好友申请列表
     * @param userId
     * @return
     */
    List<Map> listAddFriend(String userId);

    /**
     * 将当前用户的所有好友申请标记为已读
     * @param userId
     */
    void flagAddFriend(String userId);

    /**
     * 获取用户未处理的好友请求
     * @param userId
     * @return
     */
    List<Map> listNoSuccessAddFriend(String userId);

    /**
     * 新增好友
     * @param friend
     */
    void insert(Friend friend);
}
