package com.nanmeishu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = {"com.nanmeishu.transaction.mapper"})
@SpringBootApplication
@EnableFeignClients(basePackages = ("com.nanmeishu.transaction.feign"))
@EnableDiscoveryClient
public class NanmeishuTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanmeishuTransactionApplication.class, args);
    }

}
