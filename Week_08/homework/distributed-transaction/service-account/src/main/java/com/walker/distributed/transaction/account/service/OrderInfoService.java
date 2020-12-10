package com.walker.distributed.transaction.account.service;

import com.walker.distributed.transaction.account.domain.OrderInfoEntity;

public interface OrderInfoService {

    boolean saveOrderInfo(OrderInfoEntity order);
}
