package com.walker.distributed.transaction.order.service;

import com.walker.distributed.transaction.order.domain.OrderInfoEntity;

public interface PaymentService {
    boolean makePayment(OrderInfoEntity orderInfoEntity);
}
