package com.study.springcloud.controller;

import com.study.springcloud.domain.CommontResult;
import com.study.springcloud.service.AccountService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @ClassName AccountController
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 19:31
 * @Version 1.0
 **/
@RestController
public class AccountController {

    @Resource
    AccountService accountService;

    @RequestMapping("/account/decrease")
    public CommontResult decrease(@RequestParam("userId")Long userId,@RequestParam("money") BigDecimal money){
        accountService.decrease(userId,money);
        return new CommontResult(200,"扣除余额成功！");
    }
}
