package com.nanmeishu;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@SpringBootApplication
@MapperScan(basePackages = {"com.nanmeishu.user.mapper"})
public class NanmeishuUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanmeishuUserApplication.class, args);
    }

}
