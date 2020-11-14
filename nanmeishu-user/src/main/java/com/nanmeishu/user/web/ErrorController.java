package com.nanmeishu.user.web;

import com.alibaba.fastjson.JSONException;
import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.util.ResultUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class ErrorController {

    private Logger logger= LogManager.getLogger(ErrorController.class);

    @ExceptionHandler(value = {JSONException.class})
    public ResponseResult jSONException(JSONException e){
        logger.error("传入的json串格式出错："+e.toString().substring(e.toString().indexOf("Exception")+10));
        return ResultUtil.error("传入的json串格式出错："+e.toString().substring(e.toString().indexOf("Exception")+10));
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseResult missingServletRequestParameterException(MissingServletRequestParameterException e){
        return ResultUtil.error("请求缺少参数："+e.getParameterName());
    }

    //@ExceptionHandler(value = {NoTokenException.class})
    //public ResponseResult noTokenException(NoTokenException e){
    //    return ResultUtil.tokenErr(e.toString());
    //}

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseResult nullPointerException(NullPointerException e){
        logger.error("空指针异常错误："+e.toString().substring(e.toString().indexOf("Exception")+10));
        return ResultUtil.error("传入的数据有误，值："+e.toString().substring(e.toString().indexOf("Exception")+10));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseResult runtimeException(RuntimeException e){
        logger.error("自定义抛出的运行时异常："+e.toString().substring(e.toString().indexOf("Exception")+10));
        return ResultUtil.error("错误信息："+e.toString().substring(e.toString().indexOf("Exception")+10));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult httpMessageNotReadableException(HttpMessageNotReadableException e){
        logger.error("post请求缺少请求体");
        return ResultUtil.error("post请求缺少请求体");
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseResult httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        logger.error("请求类型出错，错误类型："+e.getMethod());
        return ResultUtil.error("请求类型出错,错误类型："+e.getMethod());

    }


    @ExceptionHandler(value = {Exception.class})
    private ResponseResult exception(Throwable e){
        logger.error("未知错误："+e);
        e.printStackTrace();
        return ResultUtil.error("出现未知错误，请联系管理员");
    }

}
