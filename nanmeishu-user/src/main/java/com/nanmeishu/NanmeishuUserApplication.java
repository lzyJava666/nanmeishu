package com.nanmeishu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = ("com.nanmeishu.user.feign"))
@MapperScan(basePackages = {"com.nanmeishu.user.mapper"})
public class NanmeishuUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanmeishuUserApplication.class, args);
    }

}
