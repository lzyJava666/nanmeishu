package com.nanmeishu.tale.controller;

import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.tale.entity.Statu;
import com.nanmeishu.tale.service.StatuService;
import com.nanmeishu.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequestMapping("/statu")
@RestController
public class StatuController {
    @Autowired
    private StatuService statuService;

    @GetMapping("/listStatu")
    public ResponseResult listStatu(){
        List<Statu> status= statuService.listPageStatu();
        return ResultUtil.success(status);
    }
}
