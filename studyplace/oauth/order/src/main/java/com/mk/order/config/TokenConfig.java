package com.mk.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author WXJ
 * @descrption
 * @create 2020/5/28 16:47
 **/
@Configuration
public class TokenConfig {

    //令牌存储策略
//    @Bean
//    public TokenStore tokenStore(){
//        //内存方式，生成普通令牌
//        return new InMemoryTokenStore();
//    }


    //jwt 秘钥 在资源服务和授权服务需要保持一致
    private String SIGNING_KEY = "uaa123";

    @Bean
    public TokenStore tokenStore(){
        //jwt令牌存储方案
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); //对称秘钥，资源服务器使用该秘钥来验证
        return converter;
    }
}
