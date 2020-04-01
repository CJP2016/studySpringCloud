package com.study.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author WXJ
 * @descrption
 * @create 2020/3/31 11:10
 **/
@Configuration
public class ApplicationContextConfig {

    /**
     * Ribbon负载均衡
     * LoadBalanced 轮询服务注册机（8001,8002）
     * */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
