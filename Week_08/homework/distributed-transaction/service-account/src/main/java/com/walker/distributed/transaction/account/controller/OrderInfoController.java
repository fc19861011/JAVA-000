package com.walker.distributed.transaction.account.controller;

import com.walker.distributed.transaction.account.domain.OrderInfoEntity;
import com.walker.distributed.transaction.account.reponsitory.OrderInfoRepository;
import com.walker.distributed.transaction.account.util.RWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/order")
public class OrderInfoController {

    @Autowired
    OrderInfoRepository repository;

    @PostMapping("/")
    public RWapper save(OrderInfoEntity order) {
        repository.save(order);
        return RWapper.builder().code(100).msg("保存成功").build();
    }
}
