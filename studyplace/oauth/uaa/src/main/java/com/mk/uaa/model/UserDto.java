package com.mk.uaa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/2 10:44
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;
    private String username;
    private String password;
}
