package com.nanmeishu.user.feign;

import com.nanmeishu.entity.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "nanmeishu-transaction")
public interface TransactionFeign {

    @GetMapping("/transaction/countTransactionByUserId")
    ResponseResult countTransactionByUserId(@RequestParam("userId") String userId);

}
