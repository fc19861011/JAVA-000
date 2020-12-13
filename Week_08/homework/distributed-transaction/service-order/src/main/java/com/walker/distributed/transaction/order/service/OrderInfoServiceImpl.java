package com.walker.distributed.transaction.order.service;

import cn.hutool.core.date.DateTime;
import com.walker.distributed.transaction.order.domain.OrderInfoEntity;
import com.walker.distributed.transaction.order.repository.OrderInfoRepository;
import com.walker.tx.common.enums.OrderInfoStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    OrderInfoRepository repository;

    @Autowired
    PaymentService paymentService;

    @Override
    public OrderInfoEntity saveOrderInfo(Integer count, BigDecimal amount) {
        OrderInfoEntity order = buildOrder(count, amount);
        order = repository.save(order);
        return order;
    }

    @Override
    public String orderPay(Integer count, BigDecimal amount) {
        OrderInfoEntity order = saveOrderInfo(count, amount);
        long start = System.currentTimeMillis();
        paymentService.makePayment(order);
        System.out.println("hmily-cloud分布式事务耗时：" + (System.currentTimeMillis() - start));
        return "success";
    }

    private OrderInfoEntity buildOrder(Integer count, BigDecimal amount) {
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setCount(3);
        orderInfoEntity.setCreateTime(DateTime.now());
        orderInfoEntity.setNumber("1231231232");
        orderInfoEntity.setProductId("1");
        orderInfoEntity.setUserId("10000");
        orderInfoEntity.setStatus(OrderInfoStatusEnum.NOT_PAY.getCode());
        orderInfoEntity.setTotalAmount(new BigDecimal(150));
        return orderInfoEntity;
    }

}
