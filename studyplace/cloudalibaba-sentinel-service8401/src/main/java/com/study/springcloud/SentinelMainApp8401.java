package com.study.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName SentinelMainApp8401
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 12:28
 * @Version 1.0
 **/
@EnableDiscoveryClient
@SpringBootApplication
public class SentinelMainApp8401 {
    public static void main(String[]args){
        SpringApplication.run(SentinelMainApp8401.class,args);
    }
}
