package com.study.springcloud.controller;

import com.study.springcloud.entities.CommentResult;
import com.study.springcloud.entities.Payment;
import com.study.springcloud.service.PaymentService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName PaymentController
 * @Description
 * @Author Administrator
 * @Date 2020/3/30 22:35
 * @Version 1.0
 **/
@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/payment/create")
    public CommentResult create(Payment payment){
        log.info("payment： "+payment);
        int result = paymentService.create(payment);
        log.info("插入结果： "+result);
        if (result>0){
            return  new CommentResult(200,"插入成功",result);
        }else {
            return new CommentResult(444,"插入失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommentResult getPaymentById(@PathVariable("id")Long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("查询结果： "+result);
        if (result != null){
            return  new CommentResult(200,"查询成功",result);
        }else {
            return new CommentResult(444,"没有对应记录",null);
        }
    }
}
