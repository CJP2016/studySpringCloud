package com.study.springcloud.service;

import com.study.springcloud.entities.CommentResult;
import com.study.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

/**
 * @ClassName PaymentFallbackService
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 16:18
 * @Version 1.0
 **/
@Component
public class PaymentFallbackService implements PaymentService {

    @Override
    public CommentResult<Payment> paymentSQL(Long id){
        return new CommentResult<>(444,"服务降级返回，--PaymentFallbackService",new Payment(id,"error"));
    }
}
