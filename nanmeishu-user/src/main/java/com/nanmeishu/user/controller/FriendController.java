package com.nanmeishu.user.controller;

import com.alibaba.fastjson.JSON;
import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.user.entity.Friend;
import com.nanmeishu.user.entity.User;
import com.nanmeishu.user.service.FriendService;
import com.nanmeishu.util.DataUtil;
import com.nanmeishu.util.JwtUtil;
import com.nanmeishu.util.ResultUtil;
import com.nanmeishu.web.TokenVerifyAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    @ApiOperation("获取当前用户的好友请求列表")
    @TokenVerifyAnnotation
    @GetMapping("/listAddFriend")
    public ResponseResult listAddFriend(HttpServletRequest req){
        String token = req.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        //获取当前用户的好友请求
        //List<Map> objects=friendService.listAddFriend(userId);
        //获取当前用户未处理的好友请求
        List<Map> NoSuccess=friendService.listNoSuccessAddFriend(userId);
        //将所有好友请求标记为已读
        friendService.flagAddFriend(userId);
        return ResultUtil.success(NoSuccess);
    }

    @ApiOperation("新增好友")
    @PostMapping("/insert")
    @Transactional
    public ResponseResult insert(@RequestBody String frientStr){
        System.out.println(frientStr);
        Friend friend = JSON.parseObject(frientStr, Friend.class);
        friendService.insert(friend);
        Long userId = friend.getUserId();
        friend.setFriendId(null);
        friend.setBrName(null);
        friend.setIsMyYour(null);
        friend.setIsYourMy(null);
        friend.setMesTop(null);
        friend.setStatuss(null);
        friend.setGroupId(null);
        friend.setUserId(friend.getMyUserId());
        friend.setMyUserId(userId);
        friendService.insert(friend);
        return ResultUtil.success();
    }

    @ApiOperation("返回当前用户和目标用户的所有聊天记录")
    @TokenVerifyAnnotation
    @GetMapping("/listChatByFromUser")
    public ResponseResult listChatByFromUser(@RequestParam("fromId") String fromId,HttpServletRequest request){
        String token = request.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        List<Map> resList=friendService.listChatByFromUser(userId,fromId);
        return ResultUtil.success(resList);
    }

    @ApiOperation("返回当前用户最近聊天列表---最后一条聊天记录")
    @TokenVerifyAnnotation
    @GetMapping("/listChatByToken")
    public ResponseResult listChatByToken(HttpServletRequest request){
        String token = request.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        List<Map> resList=friendService.listChatByToken(userId);
        return ResultUtil.success(resList);
    }

    @ApiOperation("通过用户id和本人id获取到好友信息")
    @TokenVerifyAnnotation
    @GetMapping("/getFriendByFromId")
    public ResponseResult getFriendByFromId(@RequestParam("fromId") String fromId,HttpServletRequest request){
        String token = request.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        Friend friend=friendService.getFriendByFromId(userId,fromId);
        return ResultUtil.success(friend);
    }

    @ApiOperation("修改好友权限")
    @TokenVerifyAnnotation
    @PostMapping("/updateFriend")
    public ResponseResult updateFriend(@RequestBody Friend friend,HttpServletRequest request){
        DataUtil.verifyData(friend.getUserId(),"userId/好友Id不能为空");
        String token = request.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        friend.setMyUserId(Long.parseLong(userId));
        friendService.updateFriend(friend);
        return ResultUtil.success();
    }

    @ApiOperation("删除目标好友")
    @TokenVerifyAnnotation
    @GetMapping("/deleteFriend")
    public ResponseResult deleteFriend(@RequestParam("fromId") String fromId,HttpServletRequest request){
        String token = request.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        friendService.deleteFriend(fromId,userId);
        return ResultUtil.success();
    }

}
