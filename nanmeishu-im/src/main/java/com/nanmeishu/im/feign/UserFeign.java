package com.nanmeishu.im.feign;

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

    @PostMapping("/user/login")
    ResponseResult login(@RequestBody Map<String, Object> map);



}
