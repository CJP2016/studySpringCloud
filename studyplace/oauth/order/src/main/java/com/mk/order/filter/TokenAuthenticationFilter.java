package com.mk.order.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mk.order.model.User;
import com.mk.utils.EncryptUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/3 16:57
 **/
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取header头中的token
        String token = httpServletRequest.getHeader("json-token");
        System.out.println(token);
        if (token!=null){
            //解析token
            String jsonToken = EncryptUtil.decodeUTF8StringBase64(token);
            //将token转换成json
            JSONObject jsonObject = JSON.parseObject(jsonToken);



            //用户身份信息
//            String principal = jsonObject.getString("principal");
//            User user = new User();
//            user.setUsername(principal);
            User user= JSON.parseObject(jsonObject.getString("principal"),User.class);
            //用户权限
            JSONArray authoritiesArray = jsonObject.getJSONArray("authorities");
            String[]  authorities = authoritiesArray.toArray(new String[authoritiesArray.size()]);

            //2.将身份信息和权限 填充到authentication
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal,null, AuthorityUtils.createAuthorityList(authorities));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null, AuthorityUtils.createAuthorityList(authorities));
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            //将authenticationToken填充到安全上下文中
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }
        //让过滤器继续执行之后的操作
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
