package com.study.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName PaymentService
 * @Description
 * @Author Administrator
 * @Date 2020/4/2 23:05
 * @Version 1.0
 **/
@Service
public class PaymentService {

    //服务降级==============

    //正常访问
    public String paymentInfo_OK(Integer id){
        return "线程池： "+Thread.currentThread().getName()+"  paymentInfo_OK,id: "+id;

    }


    /*
     * fallbackMethod---运行超时或失败时调用的返回方法
     *@HystrixProperty----限时3秒，超过即降级，调用fallbackMethod
     *
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_TimeOut(Integer id){
        int timenum = 3;
        //int age = 10/0
        try{
            TimeUnit.SECONDS.sleep(timenum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： "+Thread.currentThread().getName()+"  paymentInfo_TimeOut,id: "+id+" 耗时"+timenum+"秒钟 ";
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池： "+Thread.currentThread().getName()+"  系统繁忙，请稍后再试,id: "+id;
    }


    //=================服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开始断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id")Integer id){
        if (id < 0){
            throw new RuntimeException("id不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName() +"\t"+ "调用成功，流水号："+serialNumber;

    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能为负数——————————————-请稍后再试";
    }
}
