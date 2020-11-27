package com.nanmeishu.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * controller方法的切面
 */
@Aspect
@Component
public class ControllerAspect {
    private Logger logger= LogManager.getLogger(ControllerAspect.class);

    //为了记录执行时间 方便调试 如果不需要可以去掉
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.nanmeishu.*.controller.*.*(..))")
    public void pointCut() {}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        logger.info("--------------------当前请求开始---------------------");
        logger.info("请求进入方法："+request.getRequestURL().toString());
        logger.info("请求类型："+request.getMethod());
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        Object[] args = joinPoint.getArgs();
        StringBuffer canshu=new StringBuffer();
        for (int i = 0; i < argNames.length; i++) {
            if(args[i]==null||args[i].equals("")){
                continue;
            }
            canshu.append(argNames[i]+": "+args[i]+"  ");
        }
        logger.info("请求参数："+canshu );
    }

    //环绕执行
    //定义需要匹配的切点表达式，同时需要匹配参数
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //这句必须有 往下执行方法
        Object result = pjp.proceed();
        logger.info("返回数据：{}", result);
        logger.info("==================当前请求完成==================>");
        return result;
    }

//    @After("pointCut()")
//    public void after(JoinPoint point) {
//        logger.info("返回数据：", Arrays.toString(point.getArgs()));
//        logger.info("==================当前请求完成==================>");
//    }
}
