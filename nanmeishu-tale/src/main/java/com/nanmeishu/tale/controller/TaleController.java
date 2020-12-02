package com.nanmeishu.tale.controller;

import com.alibaba.fastjson.JSON;
import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.tale.entity.Tale;
import com.nanmeishu.tale.feign.UserFeign;
import com.nanmeishu.tale.service.TaleService;
import com.nanmeishu.util.DataUtil;
import com.nanmeishu.util.ResultUtil;
import com.nanmeishu.web.TokenVerifyAnnotation;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Api(tags = "故事模块接口")
@RequestMapping("/tale")
@RestController
public class TaleController {

    @Autowired
    TaleService taleService;

    @Autowired
    UserFeign userFeign;

    @ApiOperation("/新增故事及其明细")
    @PostMapping("/saveTaleAndDetails")
    public ResponseResult saveTaleAndDetails(@RequestBody Tale tale){
        saveTaleAndDetailsVerify(tale);
        taleService.saveTaleAndDetails(tale);
        return ResultUtil.success();
    }

    //新增故事及其明细---数据有效性验证
    private void saveTaleAndDetailsVerify(Tale tale) {
        DataUtil.verifyData(tale.getFrontDate(),"故事开始日期/frontDate");
        DataUtil.verifyData(tale.getEndDate(),"故事结束日期/endDate");
        if(tale.getCreateTime()==null){
            tale.setCreateTime(LocalDateTime.now());
        }
        DataUtil.verifyData(tale.getType(),"故事类型/type");
        DataUtil.verifyData(tale.getTaleDetails().getTaleTitle(),"故事标题/taleTitle");
        if(tale.getTaleDetails().getContent()==null){
            tale.getTaleDetails().setContent("");
        }
        if(tale.getType()==1&&!tale.getFrontDate().toString().equals(tale.getEndDate().toString())){
            throw new RuntimeException("日期的开始日期和结束日期都应该在同一天！");
        }

        //判断用户是否存在于用户服务最后
        DataUtil.verifyData(tale.getUserId(),"用户ID/userId");
        ResponseResult responseResult = userFeign.get(tale.getUserId().toString());
        if(responseResult.getData()==null||responseResult.getData().equals("")){
            throw new RuntimeException("所属用户必须是合法用户");
        }
    }

    @ApiOperation("获取用户故事列表")
    @GetMapping("/listByUserId")
    @ApiImplicitParam(name = "userId",value = "用户id",required = true)
    @ApiResponses({
            @ApiResponse(code = 200,message = "故事对象",response = Tale.class)
    })
    public ResponseResult listByUserId(@RequestParam("userId") String userId){
        DataUtil.verifyData(userId,"用户id/userId");
        return ResultUtil.success(taleService.listByUserId(userId));
    }


    @TokenVerifyAnnotation
    @GetMapping("/aaa")
    public ResponseResult getTest(){
        return ResultUtil.success("success");
    }


}
