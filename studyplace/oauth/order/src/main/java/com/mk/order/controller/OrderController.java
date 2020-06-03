package com.mk.order.controller;

import com.mk.order.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/2 15:42
 **/
@RestController
public class OrderController {

    @GetMapping(value = "/r1")
    @PreAuthorize("hasAuthority('p1')") //拥有p1权限方可访问此url
    public String r1(){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getPassword());
//        return user.getUsername()+"访问资源1";
        return username+"访问资源1";
    }
}
