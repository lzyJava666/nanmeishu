package com.nanmeishu.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接口响应类
 */
@ApiModel("接口响应类")
public class ResponseResult {
    @ApiModelProperty("响应代码")
    private int errcode ;
    @ApiModelProperty("响应数据")
    private Object data = "";
    @ApiModelProperty("响应消息")
    private String errmsg = "";

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

    public ResponseResult() {

    }

    public ResponseResult(int errcode, Object data, String errmsg) {
        this.errcode = errcode;
        this.data = data;
        this.errmsg = errmsg;
    }

    public ResponseResult(int errcode, Object data, StringBuffer errmsg) {
        this.errcode = errcode;
        this.data = data;
        this.errmsg = errmsg.toString();
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
