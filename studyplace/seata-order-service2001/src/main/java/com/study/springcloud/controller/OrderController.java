package com.study.springcloud.controller;

import com.study.springcloud.domain.CommontResult;
import com.study.springcloud.domain.Order;
import com.study.springcloud.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName OrderController
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 19:06
 * @Version 1.0
 **/
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    public CommontResult create(Order order){
        orderService.create(order);
        return new CommontResult(200,"订单创建成功");
    }
}
