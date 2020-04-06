package com.study.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.study.springcloud.entities.CommentResult;
import com.study.springcloud.entities.Payment;
import com.study.springcloud.myhandler.CustomerBlockHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RateLimitController
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 14:26
 * @Version 1.0
 **/
@RestController
@Slf4j
public class RateLimitController {
    /*
     * @Author kipp
     * @Description 返回自己定义的返回结果
     * @Date 14:35 2020/4/6
     * @Param []
     * @return com.study.springcloud.entities.CommentResult
     **/
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommentResult byResource(){
        return new CommentResult(200,"按资源名称限流测试OK",new Payment(2020L,"serial001"));
    }

    public CommentResult handleException(BlockException exception){
        return new CommentResult(444,exception.getClass().getCanonicalName()+"\t 服务不可用");
    }


    /*
     * @Author kipp
     * @Description 使用自带的返回页面
     * @Date 14:34 2020/4/6
     * @Param []
     * @return com.study.springcloud.entities.CommentResult
     **/
    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommentResult byUrl(){
        return new CommentResult(200,"按url限流测试OK",new Payment(2020L,"serial002"));
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",blockHandlerClass = CustomerBlockHandler.class,blockHandler = "handlerException2")
    public CommentResult customerBlockHandler(){
        return new CommentResult(200,"按客户自定义",new Payment(2020L,"serial003"));
    }
}
