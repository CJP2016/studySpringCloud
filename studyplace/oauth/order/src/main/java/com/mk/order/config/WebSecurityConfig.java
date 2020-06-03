package com.mk.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/2 16:30
 **/
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //安全拦截机制（重要）
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/r/r1").hasAnyAuthority("p2")
//                .antMatchers("/r/r2").hasAnyAuthority("p2")
                .antMatchers("/r/**").authenticated() //所有/r/**的请求必须认证通过
                .anyRequest().permitAll() //除了/r/**,其他的请求都可以访问
        ;
    }
}
