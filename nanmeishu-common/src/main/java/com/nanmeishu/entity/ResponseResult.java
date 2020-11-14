package com.nanmeishu.entity;

/**
 * 接口响应类
 */
public class ResponseResult {
    private int errcode ;
    private Object data = "";
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

    @Override
    public String toString() {
        return "ResponseResult{" +
                "errcode='" + errcode + '\'' +
                ", data=" + data +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
