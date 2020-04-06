package com.study.springcloud.dao;

import com.study.springcloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {
    void crate(Order order);

    void update(@Param("userId") Long userId,@Param("status") Integer status);
}
