package com.nanmeishu.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nanmeishu.user.entity.User;

import java.util.Map;

public interface UserService extends IService<User> {
    /**
     * 注册接口
     *
     * @param user
     */
    void register(User user);

    /**
     * 修改用户信息
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 修改用户密码
     *
     * @param userId
     * @param reqMap
     */
    void updatePas(String userId, Map<String, Object> reqMap);

    /**
     * 返回用户人生进度条
     */
    Map<String, Object> getUserProgressBars(String userId);
}
