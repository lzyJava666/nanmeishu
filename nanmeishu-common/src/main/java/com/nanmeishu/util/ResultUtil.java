package com.nanmeishu.util;

import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.enums.ErrorCodeEnum;

/**
 * 响应工具类
 */
public class ResultUtil {


    public static ResponseResult success() {
        ResponseResult responseResult = ResponseResult.getInstance();
        responseResult.setErrcode(ErrorCodeEnum.seccess.getCode());
        responseResult.setData("");
        responseResult.setErrmsg(ErrorCodeEnum.seccess.name());
        return responseResult;
    }

    public static ResponseResult success(Object o) {
        ResponseResult responseResult = ResponseResult.getInstance();
        responseResult.setErrcode(ErrorCodeEnum.seccess.getCode());
        responseResult.setData(o);
        responseResult.setErrmsg(ErrorCodeEnum.seccess.name());
        return responseResult;
    }

    public static ResponseResult error() {
        ResponseResult responseResult = ResponseResult.getInstance();
        responseResult.setErrcode(ErrorCodeEnum.error.getCode());
        responseResult.setData("");
        responseResult.setErrmsg(ErrorCodeEnum.error.name());
        return responseResult;
    }

    public static ResponseResult error(String msg) {
        ResponseResult responseResult = ResponseResult.getInstance();
        responseResult.setErrcode(ErrorCodeEnum.error.getCode());
        responseResult.setData("");
        responseResult.setErrmsg(msg);
        return responseResult;
    }

    public static ResponseResult error(StringBuffer msg) {
        ResponseResult responseResult = ResponseResult.getInstance();
        responseResult.setErrcode(ErrorCodeEnum.error.getCode());
        responseResult.setData("");
        responseResult.setErrmsg(msg.toString());
        return responseResult;
    }
}
