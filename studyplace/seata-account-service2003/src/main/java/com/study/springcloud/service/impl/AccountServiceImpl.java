package com.study.springcloud.service.impl;

import com.study.springcloud.dao.AccountDao;
import com.study.springcloud.domain.CommontResult;
import com.study.springcloud.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @ClassName AccountServiceImpl
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 19:29
 * @Version 1.0
 **/
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
    log.info("----->扣除余额操作开始");
        accountDao.decrease(userId, money);
        log.info("--->扣除余额操作结束");
    }
}
