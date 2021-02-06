package com.nanmeishu.tale.web;

import com.nanmeishu.entity.ResponseResult;
import com.nanmeishu.tale.feign.UserFeign;
import com.nanmeishu.util.SpringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class TokenVerifyAspect {

    //有@GetNoTokenAnnotation注释的方法处理，需判断token是否可用
    @Before(value = "@annotation(com.nanmeishu.web.TokenVerifyAnnotation)")
    public void tokenVerifyBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        String token = req.getHeader("accessToken");
        if (null == token) {
            throw new RuntimeException("noToken");
        } else {
            if (!isToken(token)) {
                throw new RuntimeException("noToken");
            }
        }
    }

    /**
     * 判断token是否有效
     *
     * @param token
     * @return
     */
    private boolean isToken(String token) {
        UserFeign redisUtil = SpringUtil.getBean(UserFeign.class);
        ResponseResult responseResult = redisUtil.verifyToken(token);
        return (boolean) responseResult.getData();
    }


}
