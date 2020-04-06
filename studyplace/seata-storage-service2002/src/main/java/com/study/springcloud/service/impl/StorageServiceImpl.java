package com.study.springcloud.service.impl;

import com.study.springcloud.dao.StorageDao;
import com.study.springcloud.domain.CommontResult;
import com.study.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName StorageServiceImpl
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 19:28
 * @Version 1.0
 **/
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageDao storageDao;
    @Override
    public void decrease(Long productId, Integer count) {
        log.info("------->storage-Service 减库存start");
        storageDao.decrease(productId,count);
        log.info("-------->storage减库存end");
    }
}
