package com.walker.insert.service;

import com.walker.insert.domain.OrderInfoEntity;

import java.util.List;

/**
 * @author dell
 * @date 2020/12/2 18:36
 **/
public interface OrderInfoService {
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

    /**
     * 插入订单数据（insert into values (),()...的方式）
     * @param orderInfoEntities
     */
    void insertValues(List<OrderInfoEntity> orderInfoEntities);

}
