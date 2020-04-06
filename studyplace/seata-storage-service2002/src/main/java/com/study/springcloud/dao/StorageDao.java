package com.study.springcloud.dao;

import com.study.springcloud.domain.CommontResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName StorageDao
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 19:26
 * @Version 1.0
 **/
@Mapper
public interface StorageDao {

    void decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);

}
