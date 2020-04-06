package com.study.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName PaymentResult
 * @Description
 * @Author Administrator
 * @Date 2020/3/30 22:11
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommontResult<T> {
    //404 not_found
    private Integer code;
    private String message;
    private T data;

    public CommontResult(Integer code, String message) {
        this(code,message,null);
    }
}
