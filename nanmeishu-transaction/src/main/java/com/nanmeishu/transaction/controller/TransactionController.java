package com.nanmeishu.transaction.controller;

import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.transaction.entity.Transaction;
import com.nanmeishu.transaction.service.TransactionService;
import com.nanmeishu.util.JwtUtil;
import com.nanmeishu.util.ResultUtil;
import com.nanmeishu.web.TokenVerifyAnnotation;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
