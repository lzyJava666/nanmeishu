package com.nanmeishu.user.web;


import com.nanmeishu.user.util.RedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RedisConfig {    //读取配置文件中的redis的ip地址
    @Value("${redis.host:disabled}")
    private String host;
    @Value("${redis.port:6379}")
    private int port;
    @Value("${redis.database:0}")
    private int database;
    @Value("${redis.password}")
    private String password;

    @Bean
    public RedisUtil getRedisUtil() {
        if (host.equals("disabled")) {
            return null;
        }
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.initPool(host, port, database, password);
        return redisUtil;
    }
}
