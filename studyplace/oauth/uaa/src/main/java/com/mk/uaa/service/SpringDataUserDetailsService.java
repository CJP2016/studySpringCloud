package com.mk.uaa.service;

import com.alibaba.fastjson.JSON;
import com.mk.uaa.dao.UserDao;
import com.mk.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WXJ
 * @descrption
 * @create 2020/5/27 11:07
 **/
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //将来连接数据库，通过账号查询用户信息
//        System.out.println(username);
//        List<String> permissions = new ArrayList<>();
//        permissions.add("p1");
//        permissions.add("p2");
//        String[] permissionArray = new String[permissions.size()];
//        permissions.toArray(permissionArray);
        UserDto userDto = userDao.getUserByUserName(username);

        if (userDto == null){
            //用户查不到，返回null，由provider来抛出异常
            return null;
        }
        //根据用户id查询用户权限
        List<String> permissions = userDao.findPermissionByUserId(userDto.getId());
        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);

        String jsonUser = JSON.toJSONString(userDto);
//        UserDetails  userDetails= User.withUsername("zhangsan").password("$2a$10$26vHeGbAWnF530ZlX79lB.GholYOPxj3bcMYN/PwAU/m8jgaT7Ih2").authorities("p1").build();
//        UserDetails  userDetails= User.withUsername(userDto.getUsername()).password(userDto.getPassword()).authorities(permissionArray).build();
        UserDetails  userDetails= User.withUsername(jsonUser).password(userDto.getPassword()).authorities(permissionArray).build();
        return userDetails;
    }
}
