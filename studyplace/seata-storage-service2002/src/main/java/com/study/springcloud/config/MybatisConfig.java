package com.study.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisConfig
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 19:08
 * @Version 1.0
 **/
@Configuration
@MapperScan({"com.study.springcloud.dao"})
public class MybatisConfig {
}
