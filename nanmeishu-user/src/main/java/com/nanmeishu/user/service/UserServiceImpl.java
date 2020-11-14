package com.nanmeishu.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanmeishu.user.entity.User;
import com.nanmeishu.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
