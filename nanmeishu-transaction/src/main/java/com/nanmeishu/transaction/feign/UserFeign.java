package com.nanmeishu.transaction.feign;

import com.nanmeishu.entity.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@FeignClient(value = "nanmeishu-user")
public interface UserFeign {

    @GetMapping("/user/get")
    public ResponseResult get(@RequestParam("userId") String userId);

    @GetMapping("/user/verifyToken")
    public ResponseResult verifyToken(@RequestParam("token") String token);

    @GetMapping("/user/getUserByToken")
    public ResponseResult getUserByToken(HttpServletRequest request);
}
