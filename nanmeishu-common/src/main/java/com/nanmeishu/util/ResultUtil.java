package com.nanmeishu.util;

import com.nanmeishu.entity.ResponseResult;

/**
 * 响应工具类
 */
public class ResultUtil {

    private static ResponseResult responseResult;

    public static ResponseResult success(){
        responseResult=new ResponseResult(200,"","success");
        return responseResult;
    }

    public static ResponseResult success(Object o){
        responseResult=new ResponseResult(200,o,"success");
        return responseResult;
    }

    public static ResponseResult error(){
        responseResult=new ResponseResult(1,"","error");
        return responseResult;
    }

    public static ResponseResult error(String msg){
        responseResult=new ResponseResult(1,"",msg);
        return responseResult;
    }

}
