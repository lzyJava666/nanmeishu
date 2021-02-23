package com.nanmeishu.user.controller;

import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.user.entity.Friend;
import com.nanmeishu.user.entity.User;
import com.nanmeishu.user.service.FriendService;
import com.nanmeishu.util.JwtUtil;
import com.nanmeishu.util.ResultUtil;
import com.nanmeishu.web.TokenVerifyAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "用户好友接口")
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @ApiOperation("通过条件查找好友")
    @TokenVerifyAnnotation
    @GetMapping("/listUserByPhoneOrName")
    public ResponseResult listUserByPhoneOrName(@RequestParam("content") String content, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        List<User> users=friendService.listUserByPhoneOrName(content,userId);
        return ResultUtil.success(users);
    }

    @ApiOperation("获取当前用户的好友列表")
    @TokenVerifyAnnotation
    @GetMapping("/listFriendByMe")
    public ResponseResult listFriendByMe(HttpServletRequest req){
        String token = req.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        List<Friend> friends= friendService.listFriendByMe(userId);
        return ResultUtil.success(friends);
    }



}
