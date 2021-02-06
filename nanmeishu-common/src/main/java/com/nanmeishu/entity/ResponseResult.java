package com.nanmeishu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 接口响应类
 */
@Data
@ApiModel("接口响应类")
public class ResponseResult {
    private static volatile ResponseResult responseResult;

    @ApiModelProperty("响应代码")
    private int errcode;
    @ApiModelProperty("响应数据")
    private Object data = "";
    @ApiModelProperty("响应消息")
    private String errmsg = "";

    private ResponseResult() {
    }

    public static ResponseResult getInstance() {
        if (responseResult == null) {
            synchronized (ResponseResult.class) {
                if (responseResult == null) {
                    responseResult = new ResponseResult();
                }
            }
        }
        return responseResult;
    }
}
