package com.mk.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author WXJ
 * @descrption
 * @create 2020/5/28 16:31
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    //认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;

    //jwt令牌
    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //配置客户端详情服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        super.configure(clients);
        clients.withClientDetails(clientDetailsService)//从
//        clients.inMemory()  //使用in-memory存储---内存方式存储客户端配置
                .withClient("c1")   //client_id
                .secret(new BCryptPasswordEncoder().encode("secret"))//客户端秘钥
                .resourceIds("res1")    //客户端可以访问的资源列表
                .authorizedGrantTypes("authorization_code","password","client_credentials","implicit","refresh_token")//该client运行的授权类型
                .scopes("all")//允许的授权范围（只填user-service，可以限制使用user-Service）
            .autoApprove(false)//授权页面
                //验证回调地址
            .redirectUris("http://www.baidu.com");
    }

    //令牌管理服务
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);//客户端信息服务
        services.setSupportRefreshToken(true);//是否产生刷新令牌
        services.setReuseRefreshToken(true);
        services.setTokenStore(tokenStore);//令牌的存储策略
        //jwt--------------------start
        //令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        services.setTokenEnhancer(tokenEnhancerChain);
        //jwt-------------------end
        services.setAccessTokenValiditySeconds(7200);//令牌默认有效期2小时
        services.setRefreshTokenValiditySeconds(259200);//刷新令牌默认有效期3天
        return services;
    }

    //配置令牌(token)的访问端点和令牌服务
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        super.configure(endpoints);
        endpoints.authenticationManager(authenticationManager)  //认证管理器
                .authorizationCodeServices(authorizationCodeServices)//授权码服务
                .tokenServices(tokenServices())//令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);//允许post请求
    }

    //配置令牌端点的安全约束，安全策略
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        super.configure(security);
        security.tokenKeyAccess("permitAll()")          //oauth/token_key公开  允许所有人访问
                .checkTokenAccess("permitAll()")        //oauth/check_token公开  允许所有人访问
                .allowFormAuthenticationForClients()    //表单认证，申请令牌
        ;
    }



    //设置授权码模式的授权码如何存取
//    @Bean
//    public AuthorizationCodeServices authorizationCodeServices(){
//        //暂时使用内存方式
//        return new InMemoryAuthorizationCodeServices();
//    }

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    //从将客户端的信息存储到数据库  ，使用webSecurityConfig里的密码加密方式
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource){
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService)clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

}
