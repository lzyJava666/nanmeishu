package com.nanmeishu.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nanmeishu.user.entity.Friend;
import com.nanmeishu.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendMapper extends BaseMapper<Friend> {
    /**
     * 通过手机号或者昵称 查找用户列表，且不返回已成为好友关系的用户
     * @param content 通过手机号或者昵称
     * @param  userId 查找人userId
     * @return 用户列表
     */
    List<User> listUserByPhoneOrName(@Param("content") String content,@Param("userId") String userId);

    /**
     * 获取当前用户的好友列表
     */
    List<Friend> listFriendByMe(@Param("userId") String userId);
}
