package com.nanmeishu.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nanmeishu.user.constant.AllConstant;
import com.nanmeishu.user.entity.User;
import com.nanmeishu.user.mapper.UserMapper;
import com.nanmeishu.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void register(User user) {
        //判断用户是否合法
        List<User> userVerify = userMapper.selectList(new QueryWrapper<User>().eq("username", user.getUsername()).or().eq("phone", user.getPhone()));
        if(userVerify!=null&&userVerify.size()>0){
            throw new RuntimeException("用户名/手机号已被使用，请重试");
        }
        //注册
        int i = userMapper.insert(user);
        if(i<=0){
            throw new RuntimeException("注册失败！请检查数据合理性！");
        }
    }

    @Override
    public void updateUser(User user) {
        //判断修改的信息是否合法
        //判断手机号是否重复
        if(user.getPhone()!=null){
            User ifUserByPhone = userMapper.selectOne(new QueryWrapper<User>().eq("phone", user.getPhone()));
            if(ifUserByPhone!=null){
                throw new RuntimeException("修改的手机号码已存在！");
            }
        }
        //判断身份证是否重复
        if(user.getIdentityCard()!=null){
            User ifUserByIdentityCard = userMapper.selectOne(new QueryWrapper<User>().eq("identity_card", user.getIdentityCard()));
            if(ifUserByIdentityCard!=null){
                throw new RuntimeException("修改的身份证号已存在！");
            }
        }
        //修改
        int u = userMapper.updateById(user);
        if(u<=0){
            throw new RuntimeException("修改出错");
        }
    }

    @Override
    public void updatePas(String userId, Map<String, Object> reqMap) {
        //验证用户信息
        //旧密码
        String formerPas=reqMap.get("formerPas").toString();
        //新密码
        String newPas=reqMap.get("newPas").toString();
        //手机号
        String phone=reqMap.get("phone").toString();
        User userVerify=userMapper.selectOne(new QueryWrapper<User>()
                .eq("password", DataUtil.md5Encrypt(formerPas,AllConstant.getPasMd5()))
                .eq("user_id",userId));
        if(userVerify!=null &&userVerify.getPhone().equals(phone))
        {
            //验证成功
            User user=new User();
            user.setUserId(Long.parseLong(userId));
            user.setPassword(DataUtil.md5Encrypt(newPas, AllConstant.getPasMd5()));
            int u = userMapper.updateById(user);
            if(u<=0){
                throw new RuntimeException("修改出错");
            }
        }else{
            throw new RuntimeException("验证用户信息出错");
        }
    }
}
