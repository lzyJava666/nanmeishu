package com.nanmeishu.tale.feign;

import com.nanmeishu.entity.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("nanmeishu-user")
public interface UserFeign {

    @GetMapping("/user/get")
    public ResponseResult get(@RequestParam("userId") String userId);

}
