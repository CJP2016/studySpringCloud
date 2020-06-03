package com.mk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/2 15:46
 **/
@Configuration
public class ResourceServerConfig  {

    public static final String RESOURCE_ID = "res1";

    //uaa资源
    @Configuration
    @EnableResourceServer
    class UAAServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID)       //资源id
                    .tokenStore(tokenStore)   //jwt 验证
                    .stateless(true);
        }

        //安全策略
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/uaa/**").permitAll();
        }
    }

    //order资源
    @Configuration
    @EnableResourceServer
    class OrderServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID)       //资源id
                    .tokenStore(tokenStore)   //jwt 验证
                    .stateless(true);
        }

        //安全策略
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/order/**").access("#oauth2.hasScope('ROLE_API')");
        }
    }
}


