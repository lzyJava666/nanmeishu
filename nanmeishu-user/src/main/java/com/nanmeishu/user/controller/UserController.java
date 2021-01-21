package com.nanmeishu.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.user.constant.AllConstant;
import com.nanmeishu.user.entity.User;
import com.nanmeishu.user.service.UserService;
import com.nanmeishu.user.util.RedisUtil;
import com.nanmeishu.util.JwtUtil;
import com.nanmeishu.web.TokenVerifyAnnotation;
import com.nanmeishu.util.DataUtil;
import com.nanmeishu.util.ResultUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@Api(tags = "用户接口")
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
    @ApiOperation("用戶登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "body", required = true),
            @ApiImplicitParam(name = "password", value = "密码（MD5加密）", paramType = "body", required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "令牌token", response = String.class)
    })
    public ResponseResult login(@RequestBody Map<String, Object> map) {
        loginVerify(map);
        String username = String.valueOf(map.get("username"));
        String password = String.valueOf(map.get("password"));
        //String password= DataUtil.md5Encrypt(String.valueOf(map.get("password")), AllConstant.getPasMd5());
        //验证登录是否成功
        User user = userService.getOne(new QueryWrapper<User>()
                .eq("username", username)
                .eq("password", password));
        if (user == null) {
            throw new RuntimeException("用户名或者密码出错！");
        }
        //生成token
        String token = JwtUtil.sign(username, user.getUserId().toString());
        if (token != null) {
            //存入redis中
            Jedis jedis = redisUtil.getJedis();
            try {
                jedis.set(token, LocalDateTime.now().plusHours(2).minusMinutes(10).toString());
                jedis.expire(token, Integer.parseInt(TOKEN_DATE) - 60 * 60 * 10);
            } finally {
                jedis.close();
            }
            return ResultUtil.success(token);
        } else {
            throw new RuntimeException("系统出错");
        }
    }

    //登录接口数据有效性验证
    private void loginVerify(Map<String, Object> map) {
        DataUtil.verifyData(map.get("username").toString(), "用户名/username");
        DataUtil.verifyData(map.get("password").toString(), " 密码/password");
    }

    @ApiOperation("注册接口")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody @Valid User user) {
        registerVerify(user);
        userService.register(user);
        return ResultUtil.success();
    }

    //注册接口--数据有效性验证
    private void registerVerify(User user) {
        DataUtil.verifyData(user.getPassword(), "密码/password");
        DataUtil.verifyData(user.getPhone(), "手机号/phone");
        DataUtil.verifyData(user.getUsername(), "用户名/username");
        if (user.getCreateTime() == null) {
            user.setCreateTime(LocalDateTime.now());
        }
        user.setPassword(DataUtil.md5Encrypt(user.getPassword(), AllConstant.getPasMd5()));
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/update")
    @TokenVerifyAnnotation
    public ResponseResult update(@RequestBody User user, HttpServletRequest request) {
        updateVerify(user, request);
        userService.updateUser(user);
        return ResultUtil.success();
    }

    //修改用户信息数据有效性验证
    private void updateVerify(User user, HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        DataUtil.verifyData(userId, "用户ID/userId");
        user.setUserId(Long.parseLong(userId));
        user.setCreateTime(null);
        user.setPassword(null);
    }

    public ResponseResult verify(@RequestParam("token") String token) {
        if (JwtUtil.verify(token)) {
            return ResultUtil.success(JwtUtil.get(token, "userId"));
        } else {
            return ResultUtil.error();
        }
    }

    @ApiOperation("修改密码")
    @PostMapping("/updatePas")
    @TokenVerifyAnnotation
    @ApiImplicitParams({
            @ApiImplicitParam(name = "formerPas", value = "旧密码", required = true, paramType = "body"),
            @ApiImplicitParam(name = "newPas", value = "新密码", required = true, paramType = "body"),
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "body"),
    })
    public ResponseResult updatePas(@RequestBody Map<String, Object> reqMap, HttpServletRequest request) {
        updatePasVerify(reqMap);
        String token = request.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        userService.updatePas(userId, reqMap);
        return ResultUtil.success();
    }

    //找回密码数据有效性验证
    private void updatePasVerify(Map<String, Object> reqMap) {
        DataUtil.verifyData(reqMap.get("formerPas"), "旧密码/formerPas");
        DataUtil.verifyData(reqMap.get("newPas"), "新密码/newPas");
        DataUtil.verifyData(reqMap.get("phone"), "手机号/phone");
    }

    @ApiOperation("找回密码")
    @PostMapping("/getBackPas")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "手机号", required = true, paramType = "body"),
            @ApiImplicitParam(name = "password", value = "新密码", required = true, paramType = "body")
    })
    public ResponseResult getBackPas(@RequestBody Map<String, Object> reqMap) {
        getBackPasVerify(reqMap);
        User user = userService.getOne(new QueryWrapper<User>().eq("phone", reqMap.get("phone").toString()));
        if (user == null) {
            throw new RuntimeException("当前手机号未注册");
        }
        user.setPassword(DataUtil.md5Encrypt(reqMap.get("password").toString(), AllConstant.getPasMd5()));
        boolean u = userService.updateById(user);
        if (!u) {
            throw new RuntimeException("出错，请重试！");
        }
        return ResultUtil.success();
    }

    //找回密码数据有效性验证
    private void getBackPasVerify(Map<String, Object> reqMap) {
        DataUtil.verifyData(reqMap.get("phone"), "手机号/phone");
        DataUtil.verifyData(reqMap.get("password"), "新密码/password");
    }

    @ApiOperation("通过id拿到用户对象")
    @GetMapping("/get")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @ApiResponses({
            @ApiResponse(code = 200, message = "用户对象", response = User.class)
    })
    public ResponseResult get(@RequestParam("userId") String userId) {
        return ResultUtil.success(userService.getById(userId));
    }

    @GetMapping("/verifyToken")
    public ResponseResult verifyToken(@RequestParam("token") String token) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            if (jedis.get(token) != null) {
                //token存在

                if (JwtUtil.verify(token)) {
                    return ResultUtil.success(true);
                }
            }
            return ResultUtil.success(false);
        } finally {
            jedis.close();
        }
    }

    @ApiOperation("通过token拿到用户信息记录")
    @TokenVerifyAnnotation
    @GetMapping("/getUserByToken")
    @ApiResponses({
            @ApiResponse(code = 200, message = "用户对象", response = User.class)
    })
    public ResponseResult getUserByToken(HttpServletRequest request) {
        String token = request.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        return get(userId);
    }

}
