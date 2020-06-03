package com.mk.uaa.model;


import lombok.Data;

/**
 * @author WXJ
 * @descrption
 * @create 2020/6/2 10:44
 **/
@Data
public class PermissionDto {
    private String id;
    private String code;
    private String description;
    private String url;

    public String getCode() {
        return code;
    }
}
