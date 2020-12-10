package com.walker.distributed.transaction.account.service;

import com.walker.distributed.transaction.account.domain.OrderInfoEntity;
import com.walker.distributed.transaction.account.reponsitory.OrderInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    OrderInfoRepository repository;

    @Override
    public boolean saveOrderInfo(OrderInfoEntity order) {
        repository.save(order);

        return true;
    }
}
