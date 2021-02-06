package com.nanmeishu;

import com.nanmeishu.im.server.NanMeiShuServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = ("com.nanmeishu.im.feign"))
public class NanmeishuImApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanmeishuImApplication.class, args);
        new NanMeiShuServer().run();
    }

}
