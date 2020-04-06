package com.study.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ConfigClientController
 * @Description
 * @Author Administrator
 * @Date 2020/4/4 12:04
 * @Version 1.0
 **/
@RestController
@RefreshScope //支持Nacos的动态刷新功能
public class ConfigClientController {

    @Value("${server.port}")
    private String serverPort;

//    @Value("${config.info}")
//    private  String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo(){
        return "serverPort: "+serverPort+"\t\n\n configInfo: "+serverPort;
    }
}
