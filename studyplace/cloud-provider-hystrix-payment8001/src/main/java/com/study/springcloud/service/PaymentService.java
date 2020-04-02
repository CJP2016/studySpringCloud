package com.study.springcloud.service;

import org.springframework.stereotype.Service;

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

    //正常访问
    public String paymentInfo_OK(Integer id){
        return "线程池： "+Thread.currentThread().getName()+"  paymentInfo_OK,id: "+id;

    }

    public String paymentInfo_TimeOut(Integer id){
        int timenum = 3;
        try{
            TimeUnit.SECONDS.sleep(timenum);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： "+Thread.currentThread().getName()+"  paymentInfo_TimeOut,id: "+id+" 耗时"+timenum+"秒钟 ";

    }
}
