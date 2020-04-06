package com.study.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.study.springcloud.entities.CommentResult;
import com.study.springcloud.entities.Payment;
import com.study.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @ClassName CircleBreakerController
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 15:06
 * @Version 1.0
 **/
@RestController
@Slf4j
public class CircleBreakerController {

    public static  final  String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
//    @SentinelResource(value = "fallback")
//    @SentinelResource(value = "fallback",fallback = "handlerFallback")//fallback之负责业务异常
//    @SentinelResource(value = "fallback", blockHandler = "blockHandler")//blockHandler之负责sentinel控制台
    @SentinelResource(value = "fallback",fallback = "handlerFallback", blockHandler = "blockHandler")
    public CommentResult<Payment> fallback(@PathVariable Long id){
        CommentResult<Payment> result = restTemplate.getForObject(SERVICE_URL+"/paymentSQL/"+id,CommentResult.class,id);
        if (id == 4){
            throw new IllegalArgumentException("IllegalArgumentException,非法参数异常——");
        }else if (result.getData() == null){
            throw new NullPointerException("NullPointerException,该ID没有对应记录，空指针异常");
        }
        return  result;
    }

    public CommentResult<Payment> handlerFallback(@PathVariable Long id,Throwable throwable){
        log.info("handlerFallback------------------------------------------");
        Payment payment = new Payment(id,"null");
//        return new CommentResult<>(111,"handlerFallback",payment);
        return new CommentResult<>(444,"兜底异常内容："+throwable.getMessage(),payment);
    }

    public CommentResult<Payment> blockHandler(@PathVariable Long id,BlockException exception){
        log.info("blockHandler------------------------------------------");
        Payment payment = new Payment(id,"null");
//        return new CommentResult<>(111,"blockHandler",payment);
        return new CommentResult<>(445,"blockHandler-sentinel限流，"+exception.getMessage(),payment);
    }

    //================OpenFeign
    @Resource
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommentResult<Payment> paymentSQL(@PathVariable("id") Long id){
        return paymentService.paymentSQL(id);
    }
}
