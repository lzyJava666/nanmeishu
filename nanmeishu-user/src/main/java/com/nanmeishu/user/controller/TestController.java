package com.nanmeishu.user.controller;

import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.util.JwtUtil;
import com.nanmeishu.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class TestController {

    @PostMapping("/login")
    public ResponseResult login(@RequestBody Map<String,Object> user){
        String username=String.valueOf(user.get("username"));
        String password=String.valueOf(user.get("password"));
        if(!username.equals("root")||!password.equals("123")){
            throw new RuntimeException("用户名或错误错误");
        }
        String userId="999";
        String token = JwtUtil.sign(username, userId);
        if(token!=null){
            return ResultUtil.success(token);
        }else{
            throw new RuntimeException("系统出错");
        }
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
