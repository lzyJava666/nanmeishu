package com.nanmeishu.user.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

/**
 * Mapper层切面
 */
@Aspect
@Component
public class MapperAspect {

    private Logger logger= LogManager.getLogger(MapperAspect.class);

    @Pointcut("execution( * com.nanmeishu.user.mapper.*.*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        logger.info("--------------------操作数据库开始--------------------");
        logger.info("请求进入方法："+joinPoint.getSignature().getName());

        logger.info("参数：" + Arrays.toString(joinPoint.getArgs()));
    }

    //环绕执行
    //定义需要匹配的切点表达式，同时需要匹配参数
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //这句必须有 往下执行方法
        Object result = pjp.proceed();
        logger.info("返回数据：{}", result);
        logger.info("==================当前数据库完成==================>");
        return result;
    }


}
