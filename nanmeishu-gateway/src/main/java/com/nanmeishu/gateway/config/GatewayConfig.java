package com.nanmeishu.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * 配置
 * @author fengqian
 * @since <pre>2019/04/09</pre>
 */
@Configuration
public class GatewayConfig {

    /**
     * webflux 静态资源配置
     * @return serverResponse
     */
    @Bean
    RouterFunction<ServerResponse> staticResourceRouter(){
        return RouterFunctions.resources("/webjars/**", new ClassPathResource("webjars/"));
    }
}
