package com.study.springcloud.service;

import com.study.springcloud.domain.CommontResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-storage-service")
public interface StorageService {

    @PostMapping(value = "/storage/decrease")
    CommontResult decrease(@RequestParam("productId")Long productId,@RequestParam("count") Integer count);
}
