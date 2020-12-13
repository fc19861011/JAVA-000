package com.walker.distributed.transaction.order.service;


import com.walker.distributed.transaction.order.domain.OrderInfoEntity;

import java.math.BigDecimal;

public interface OrderInfoService {

    OrderInfoEntity saveOrderInfo(Integer count, BigDecimal amount);
    String orderPay(Integer count, BigDecimal amount);
}
