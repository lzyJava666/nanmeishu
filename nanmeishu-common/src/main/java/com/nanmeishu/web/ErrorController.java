package com.nanmeishu.web;

import com.alibaba.fastjson.JSONException;
import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.util.ResultUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class ErrorController {

    private Logger logger = LogManager.getLogger(ErrorController.class);

    private StringBuffer errMsg;

    @ExceptionHandler(value = {JSONException.class})
    public ResponseResult jSONException(JSONException e) {
        errMsg=new StringBuffer();
        errMsg.append("传入的json串格式出错：" ).append(e.toString().substring(e.toString().indexOf("Exception") + 10));
        logger.error(errMsg);
        return ResultUtil.error(errMsg);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        errMsg=new StringBuffer();
        errMsg.append("字段：")
                .append(fieldError.getField())
                .append(" 的值必须满足以下条件：")
                .append(fieldError.getDefaultMessage());
        logger.error(errMsg);
        return ResultUtil.error(errMsg);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseResult missingServletRequestParameterException(MissingServletRequestParameterException e) {
        errMsg=new StringBuffer("请求缺少参数：").append(e.getParameterName());
        logger.error(errMsg);
        return ResultUtil.error(errMsg);
    }

    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseResult httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        errMsg=new StringBuffer("出现参数格式错误！请检查请求头是否正确");
        return ResultUtil.error(errMsg);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseResult nullPointerException(NullPointerException e) {
        errMsg=new StringBuffer()
                .append("空指针异常错误：")
                .append(e.toString().substring(e.toString().indexOf("Exception") + 10));
        logger.error(errMsg);
        return ResultUtil.error(errMsg);
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseResult runtimeException(RuntimeException e) {
        errMsg=new StringBuffer()
                .append(e.toString().substring(e.toString().indexOf("Exception") + 10));
        if(e.toString().indexOf("token")>-1){
            logger.error(errMsg);
            return ResultUtil.NoToken();
        }
        logger.error(errMsg);
        return ResultUtil.error(errMsg);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseResult httpMessageNotReadableException(HttpMessageNotReadableException e) {
        e.printStackTrace();
        errMsg=new StringBuffer("post请求缺少请求体");
        logger.error(errMsg);
        return ResultUtil.error(errMsg);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseResult httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        errMsg=new StringBuffer()
                .append("请求类型出错，错误类型：")
                .append(e.getMethod());
        logger.error(errMsg);
        return ResultUtil.error(errMsg);

    }


    @ExceptionHandler(value = {Exception.class})
    private ResponseResult exception(Throwable e) {
        errMsg=new StringBuffer()
                .append("未知错误:")
                .append(e);
        logger.error(errMsg);
        e.printStackTrace();
        return ResultUtil.error(errMsg);
    }

}
