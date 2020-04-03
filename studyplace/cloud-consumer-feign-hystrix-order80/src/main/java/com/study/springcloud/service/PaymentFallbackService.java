package com.study.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author WXJ
 * @descrption
 * @create 2020/4/3 15:40
 **/
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "PaymentFallbackService fall back";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "PaymentFallbackService-----fall back time out";
    }
}
