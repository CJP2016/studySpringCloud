package com.study.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class PaymentMain8002 {

    /*
     * @Author kipp
     * @Description 
     * @Date 21:24 2020/3/30
     * @Param [args]
     * @return void
     **/
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8002.class,args);
    }
}
