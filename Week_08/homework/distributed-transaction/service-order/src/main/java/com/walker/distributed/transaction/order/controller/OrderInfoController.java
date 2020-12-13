package com.walker.distributed.transaction.order.controller;

import com.walker.distributed.transaction.order.service.OrderInfoService;
import com.walker.distributed.transaction.order.util.RWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(value="/order")
public class OrderInfoController {

    @Autowired
    OrderInfoService orderInfoService;

    @PostMapping("/pay")
    public RWapper save(Integer count, double amount) {
        String msg = orderInfoService.orderPay(count, BigDecimal.valueOf(amount));
        return RWapper.builder().code(100).msg(msg).build();
    }
}
