package com.nanmeishu.util;

import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.enums.ErrorCodeEnum;

/**
 * 响应工具类
 */
public class ResultUtil {

    private static ResponseResult responseResult;

    public static ResponseResult success(){
        responseResult=new ResponseResult(ErrorCodeEnum.seccess.getCode(),"",ErrorCodeEnum.seccess.name());
        return responseResult;
    }

    public static ResponseResult success(Object o){
        responseResult=new ResponseResult(ErrorCodeEnum.seccess.getCode(),o,ErrorCodeEnum.seccess.name());
        return responseResult;
    }

    public static ResponseResult error(){
        responseResult=new ResponseResult(ErrorCodeEnum.error.getCode(),"",ErrorCodeEnum.error.name());
        return responseResult;
    }

    public static ResponseResult error(String msg){
        responseResult=new ResponseResult(ErrorCodeEnum.error.getCode(),"",msg);
        return responseResult;
    }

}
