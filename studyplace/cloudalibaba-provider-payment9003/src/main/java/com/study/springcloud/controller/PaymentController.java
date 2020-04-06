package com.study.springcloud.controller;

import com.study.springcloud.entities.CommentResult;
import com.study.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @ClassName PaymentController
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 14:53
 * @Version 1.0
 **/
@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap = new HashMap<>();
    static {
        hashMap.put(1L,new Payment(1L,"111111111111111111111"));
        hashMap.put(2L,new Payment(2L,"222222222222222222222"));
        hashMap.put(3L,new Payment(3L,"333333333333333333333"));
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public CommentResult<Payment> paymentSQL(@PathVariable("id") long id){
        Payment payment = hashMap.get(id);
        CommentResult<Payment> result= new CommentResult<>(200,"from mysql ,serverPort: "+serverPort,payment);
        return  result;
    }
}
