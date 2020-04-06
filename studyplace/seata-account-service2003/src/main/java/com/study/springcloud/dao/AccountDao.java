package com.study.springcloud.dao;

import com.study.springcloud.domain.CommontResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Mapper
public interface AccountDao {
    void decrease(@RequestParam("userId")Long userId, @RequestParam("money") BigDecimal money);

}
