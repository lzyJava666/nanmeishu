package com.nanmeishu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接口响应类
 */
@ApiModel("接口响应类")
public class ResponseResult {
    private static volatile ResponseResult responseResult;

    @ApiModelProperty("响应代码")
    private int errcode ;
    @ApiModelProperty("响应数据")
    private Object data = "";
    @ApiModelProperty("响应消息")
    private String errmsg = "";

    private ResponseResult(){}

    public static ResponseResult getInstance(){
        if(responseResult==null){
            synchronized (ResponseResult.class){
                if(responseResult==null){
                    responseResult=new ResponseResult();
                }
            }
        }
        return responseResult;
    }


    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "ResponseResult{" +
                "errcode='" + errcode + '\'' +
                ", data=" + data +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
