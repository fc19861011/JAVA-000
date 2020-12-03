package com.walker.insert.service;

import com.walker.insert.domain.OrderInfoEntity;

import java.util.List;

/**
 * @author dell
 * @date 2020/12/2 18:36
 **/
public interface OrderInfoJdbcService {

    /**
     * 插入订单数据（单条）
     * @param orderInfoEntity
     */
    void insert(OrderInfoEntity orderInfoEntity);

    /**
     * 插入订单数据（一次事务提交多个insert into）
     * @param orderInfoEntities
     */
    void insertBatch(List<OrderInfoEntity> orderInfoEntities);

}
