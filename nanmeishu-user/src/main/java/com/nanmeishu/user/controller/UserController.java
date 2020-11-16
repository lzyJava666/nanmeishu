package com.nanmeishu.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.user.constant.AllConstant;
import com.nanmeishu.user.entity.User;
import com.nanmeishu.user.service.UserService;
import com.nanmeishu.user.util.JwtUtil;
import com.nanmeishu.user.util.RedisUtil;
import com.nanmeishu.user.web.TokenVerifyAnnotation;
import com.nanmeishu.util.DataUtil;
import com.nanmeishu.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RedisUtil redisUtil;

    @Value("${TOKEN.DATE}")
    String TOKEN_DATE;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody Map<String,Object> map){
        String username=String.valueOf(map.get("username"));
        String password= DataUtil.md5Encrypt(String.valueOf(map.get("password")), AllConstant.getPasMd5());
        System.out.println(password);
        //验证登录是否成功
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username).eq("password", password));
        if(user==null){
            throw new RuntimeException("用户名或者密码出错！");
        }
        //生成token
        String token = JwtUtil.sign(username, user.getUserId().toString());
        if(token!=null){
            //存入redis中
            Jedis jedis = redisUtil.getJedis();
            jedis.set(token, LocalDateTime.now().plusHours(2).minusMinutes(10).toString());
            jedis.expire(token,Integer.parseInt(TOKEN_DATE)-60*60*10);
            jedis.close();
            return ResultUtil.success(token);
        }else{
            throw new RuntimeException("系统出错");
        }
    }

    @GetMapping("/test")
    @TokenVerifyAnnotation()
    public ResponseResult test(HttpServletRequest request){
        return ResultUtil.success(JwtUtil.get(request.getHeader("accessToken"),"username"));
    }
    @TokenVerifyAnnotation
    @PostMapping("/posTest")
    public ResponseResult posTest(){
        return ResultUtil.success("成功");
    }

    @GetMapping("/verify")
    public ResponseResult verify(@RequestParam("token") String token){
        if(JwtUtil.verify(token)){
            return ResultUtil.success(JwtUtil.get(token,"userId"));
        }else{
            return ResultUtil.error();
        }
    }

}
