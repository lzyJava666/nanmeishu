package com.nanmeishu.user.feign;

import com.nanmeishu.entity.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "nanmeishu-tale")
public interface TaleFeign {

    @RequestMapping(value = "/tale/countTaleByUserId",method = RequestMethod.GET)
    ResponseResult countTaleByUserId(@RequestParam("userId") String userId);
}
