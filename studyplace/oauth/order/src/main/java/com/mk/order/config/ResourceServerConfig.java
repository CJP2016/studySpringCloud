package com.mk.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/2 15:46
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    public static final String RESOURCE_ID = "res1";

    //JWT 需要把TokenConfig拉倒config里,并注入tokenstore
    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)       //资源id
                .tokenStore(tokenStore)   //jwt 验证
//                .tokenServices(tokenServices())  //验证令牌的服务
                .stateless(true);
    }

    //安全策略
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('ROLE_API')") //校验令牌访问范围
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    //jwt令牌 屏蔽远程请求授权，改为本地认证 TokenSotre
    //资源服务令牌解析服务
//    @Bean
//    public ResourceServerTokenServices tokenServices(){
//        //使用远程服务请求授权服务器校验token，必须制定校验tokne的url，client_id，client_secret
//        RemoteTokenServices services = new RemoteTokenServices();
//        services.setCheckTokenEndpointUrl("http://localhost:8888/uaa/oauth/check_token"); //请求这个地址校验token
//        services.setClientId("c1");
//        services.setClientSecret("secret");
//        return services;
//    }
}


