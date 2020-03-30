package com.study.springcloud.dao;

import com.study.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName PaymentDao
 * @Description
 * @Author Administrator
 * @Date 2020/3/30 22:19
 * @Version 1.0
 **/
@Mapper
public interface PaymentDao {
    public int create(Payment payment);

    public  Payment getPaymentById(@Param("id") Long id);
}
