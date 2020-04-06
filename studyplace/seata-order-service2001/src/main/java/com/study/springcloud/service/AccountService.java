package com.study.springcloud.service;

import com.study.springcloud.domain.CommontResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @ClassName AccountService
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 18:56
 * @Version 1.0
 **/
@FeignClient(value = "seata-account-service")
public interface AccountService {

        @PostMapping(value = "/account/decrease")
        CommontResult decrease(@RequestParam("userId")Long userId, @RequestParam("money") BigDecimal money);
}
