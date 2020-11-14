package com.nanmeishu.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NanmeishuUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanmeishuUserApplication.class, args);
    }

}
