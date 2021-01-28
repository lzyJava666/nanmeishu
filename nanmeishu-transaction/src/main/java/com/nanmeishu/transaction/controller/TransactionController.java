package com.nanmeishu.transaction.controller;

import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.transaction.entity.Transaction;
import com.nanmeishu.transaction.service.TransactionService;
import com.nanmeishu.util.DataUtil;
import com.nanmeishu.util.JwtUtil;
import com.nanmeishu.util.ResultUtil;
import com.nanmeishu.web.TokenVerifyAnnotation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Api(tags = "代办事务模块接口")
@RequestMapping("/transaction")
@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @ApiOperation("获取个人事务列表")
    @TokenVerifyAnnotation
    @GetMapping("/getById")
    public ResponseResult getById(HttpServletRequest req, @RequestParam("type") int type,
                                  @RequestParam("startDate") String startDate){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate parse = LocalDate.parse(startDate, dateTimeFormatter);
        String token = req.getHeader("accessToken");
        String userId = JwtUtil.get(token, "userId");
        List<Transaction> transactions= transactionService.getById(userId,type,parse);
        return ResultUtil.success(transactions);
    }

    @TokenVerifyAnnotation
    @ApiOperation("修改事务内容")
    @PostMapping("/update")
    public ResponseResult update(@RequestBody Transaction transaction){
        DataUtil.verifyData(transaction.getTransactionId(),"transactionId/id");
        transactionService.update(transaction);
        return ResultUtil.success();
    }


}
