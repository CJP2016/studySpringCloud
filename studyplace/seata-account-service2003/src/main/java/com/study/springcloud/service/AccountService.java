package com.study.springcloud.service;


import java.math.BigDecimal;

/**
 * @ClassName AccountService
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 18:56
 * @Version 1.0
 **/
public interface AccountService {


    void decrease(Long userId, BigDecimal money);
}
