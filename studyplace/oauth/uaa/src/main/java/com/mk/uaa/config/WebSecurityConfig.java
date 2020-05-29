package com.mk.uaa.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author WXJ
 * @descrption
 * @create 2020/5/27 10:02
 **/
@Configuration
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    //密码编码器
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }


    //安全拦截机制
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/r/**").authenticated() //所有/r/**的请求都必须认证通过
//                .anyRequest().permitAll()//除了/r/**，其他请求都可以访问
//                .and()
//                .formLogin()//允许表单登录
//                .successForwardUrl("/login-success");//自定义登录成功的跳转页面

//        http.
        http.csrf().disable()//防止跨站请求伪造的发生，屏蔽CSBF控制
                .authorizeRequests()
                .antMatchers("/r/r1").hasAuthority("p1")
//                .antMatchers("/r/r2").hasAuthority("p2")
                .antMatchers("/r/r3").hasAnyAuthority("p1","p2")//可以允许多个
                .antMatchers("/r/**").authenticated()
                .anyRequest().permitAll()//除了/r/**，其他请求都可以访问,要放在拦截的最后，不然拦截会失效
                .and()
                .formLogin()//允许表单登录
//                .loginPage("/login-view")//自定义登录页
//                .loginProcessingUrl("/login") //自定义url
                .successForwardUrl("/login-success")//自定义登录成功的跳转页面
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                //IF_REQUIRED 每次会话都会生成一个session
                //NEVER 不会创建Session，但如果应用中其他地方创建了session，那么security将会使用它
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login-view?logout")
//            .logoutSuccessHandler(logoutSuccessHandler)
//            .addLogoutHandler(logoutHandler)
//            .invalidateHttpSession(true)
        ;
    }
}
