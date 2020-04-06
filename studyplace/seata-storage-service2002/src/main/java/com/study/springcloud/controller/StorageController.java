package com.study.springcloud.controller;

import com.study.springcloud.domain.CommontResult;
import com.study.springcloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName StorageController
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 19:29
 * @Version 1.0
 **/
@RestController
public class StorageController {
    @Autowired
    private StorageService storageService;

    @RequestMapping("/storage/decrease")
    public CommontResult decrease(Long productId, Integer count){
        storageService.decrease(productId,count);
        return new CommontResult(200,"扣减库存成功!");
    }
}
