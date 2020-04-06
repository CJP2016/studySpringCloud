package com.study.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.study.springcloud.entities.CommentResult;
import com.study.springcloud.entities.Payment;

/**
 * @ClassName CustomerBlockHandler
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 14:37
 * @Version 1.0
 **/
public class CustomerBlockHandler {
    public static CommentResult handlerException(BlockException exception){
        return new CommentResult(444,"按客户自定义，global handlerException ---------1");
    }

    public static CommentResult handlerException2(BlockException exception){
        return new CommentResult(4444,"按客户自定义，global handlerException--------2");
    }
}
