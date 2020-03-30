package com.study.springcloud.service;

import com.study.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName PaymentService
 * @Description
 * @Author Administrator
 * @Date 2020/3/30 22:32
 * @Version 1.0
 **/
public interface PaymentService {
    public int create(Payment payment);

    public  Payment getPaymentById(@Param("id") Long id);
}
