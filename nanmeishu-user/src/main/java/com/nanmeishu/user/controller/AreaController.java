package com.nanmeishu.user.controller;

import com.alibaba.fastjson.JSON;
import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.user.entity.Area;
import com.nanmeishu.user.entity.AreaDetails;
import com.nanmeishu.user.service.AreaService;
import com.nanmeishu.util.DataUtil;
import com.nanmeishu.util.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Api(tags = "地区接口")
@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    AreaService areaService;

    /**
     * 拿到所有地区及其详情
     * @return
     */
    @ApiResponses({
            @ApiResponse(code = 200,message = "地区对象列表",response = Area.class)
    })
    @ApiOperation("拿到所有地区及其详情列表")
    @GetMapping("/listAreaAndDetails")
    public ResponseResult listAreaAndDetails(){
        return ResultUtil.success(areaService.listAreaAndDetails());
    }

    @ApiResponses({
            @ApiResponse(code = 200,message = "地区详情对象列表",response = AreaDetails.class)
    })
    @ApiOperation("通过地区ID获取到对应地区详情列表")
    @GetMapping("/listDetailsByAreaId")
    public ResponseResult listDetailsByAreaId(@RequestParam("areaId") String areaId){
        DataUtil.verifyData(areaId,"地区ID/areaId");
        return ResultUtil.success(areaService.listDetailsByAreaId(areaId));
    }

    @PostMapping("/listSave")
    public ResponseResult listSave(@RequestBody String json){
        List<Map> lists = JSON.parseArray(json, Map.class);
        areaService.listSave(lists);

        return ResultUtil.success("success");
    }

}
