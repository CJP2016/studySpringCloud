package com.study.springcloud.dao;

import com.study.springcloud.domain.CommontResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @ClassName StorageDao
 * @Description
 * @Author Administrator
 * @Date 2020/4/6 19:26
 * @Version 1.0
 **/
@Mapper
public interface StorageDao {

    void decrease(@Param("productId") Long productId, @Param("count") Integer count);

}
