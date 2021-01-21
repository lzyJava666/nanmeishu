package com.nanmeishu.user.web;

import com.nanmeishu.user.util.RedisUtil;
import com.nanmeishu.util.JwtUtil;
import com.nanmeishu.util.SpringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class TokenVerifyAspect {

    //有@GetNoTokenAnnotation注释的方法处理，需判断token是否可用
    @Before(value = "@annotation(com.nanmeishu.web.TokenVerifyAnnotation)")
    public void tokenVerifyBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        String token = req.getHeader("accessToken");
        if(null==token){
            throw new RuntimeException("token不存在");
        }else{
            if(!isToken(token)){
                throw new RuntimeException("您的token不正确或者已失效");
            }
        }
    }

    /**
     * 判断token是否有效
     * @param token
     * @return
     */
    private boolean isToken(String token) {
        RedisUtil redisUtil= SpringUtil.getBean(RedisUtil.class);
        Jedis jedis = redisUtil.getJedis();
        if(jedis.get(token)!=null){
            //token存在
            jedis.close();
            if(JwtUtil.verify(token)) {
                return true;
            }
        }
        return false;
    }


}
