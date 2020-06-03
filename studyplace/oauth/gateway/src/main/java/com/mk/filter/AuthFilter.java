package com.mk.filter;

import com.alibaba.fastjson.JSON;
import com.mk.utils.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/3 16:07
 **/
@Component
public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        //1.从安全的上下文中获取token内容
        RequestContext ctx = RequestContext.getCurrentContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof OAuth2Authentication)){
           //无token访问网关内资源情况，目前仅有uaa服务直接暴露。
           return null;
        }
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();


//        Object principal = userAuthentication.getPrincipal();
        //2.组装明文token，转发给服务，放入header，名称为json-token

        //取出用户身份
        String principal = userAuthentication.getName();

        //取出用户权限
        List<String> authorities = new ArrayList<>();
        //从userAuthentication取出权限，放在authorities
        userAuthentication.getAuthorities().stream().forEach(s -> authorities.add(((GrantedAuthority)s).getAuthority()));

        //获取Oauth2的请求数据
        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String,String> requestParameters = oAuth2Request.getRequestParameters();
        Map<String,Object> jsonToken = new HashMap<>(requestParameters);
        if (userAuthentication != null){
            jsonToken.put("principal",principal);
            jsonToken.put("authorities",authorities);
        }
        //把身份信息和权限信息放在json中，加入http的header中
        ctx.addZuulRequestHeader("json-token", EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken)));

        return null;

    }
}
