package com.mk.uaa.dao;

import com.fasterxml.jackson.databind.BeanProperty;
import com.mk.uaa.model.PermissionDto;
import com.mk.uaa.model.UserDto;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/2 10:45
 **/
@Component
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public UserDto getUserByUserName(String username) {
        String sql = "select * from user where username = ?";
        List<UserDto> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(UserDto.class),username);
        list.forEach(c -> System.out.println(c));
        return list.get(0);
    }

    public List<String> findPermissionByUserId(int userId) {
        String sql = "SELECT * FROM permission where id in ( select permission_id from role_permission where role_id in(select role_id from user_role where user_id = ?))";
//        List<PermissionDto> list = jdbcTemplate.queryForList(sql,new Object[]{userId},PermissionDto.class);
        List<PermissionDto> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(PermissionDto.class),userId);
        List<String> permissions = new ArrayList<>();
        list.forEach(c -> permissions.add(c.getCode()));
        return permissions;
    }
}
