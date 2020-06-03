package com.mk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/3 11:19
 **/
@SpringBootApplication
@EnableEurekaServer
public class DiscoverMain {
    public static void main(String[] args) {
        SpringApplication.run(DiscoverMain.class,args);
    }
}
