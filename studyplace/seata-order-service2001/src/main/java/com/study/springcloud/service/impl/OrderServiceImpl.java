package com.study.springcloud.service.impl;

import com.study.springcloud.dao.OrderDao;
import com.study.springcloud.domain.Order;
import com.study.springcloud.service.AccountService;
import com.study.springcloud.service.OrderService;
import com.study.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName OrderServiceImpl
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 18:57
 * @Version 1.0
 **/
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private StorageService storageService;

    @Resource
    private AccountService accountService;

    @Override
    public void create(Order order) {
        log.info("---->新建订单");
        orderDao.crate(order);

        log.info(("---->开始调用库存，扣减库存"));
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("----->库存扣减end");

        log.info("---->扣余额");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("----->余额end");

        //修改订单状态
        log.info("----------->修改订单状态");
        orderDao.update(order.getUserId(),0);
        log.info("--------->修改订单状态end");

        log.info("-------->下订单结束");
    }
}
