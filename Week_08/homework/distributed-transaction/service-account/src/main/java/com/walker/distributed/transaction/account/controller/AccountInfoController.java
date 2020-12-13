package com.walker.distributed.transaction.account.controller;

import com.walker.distributed.transaction.account.service.AccountInfoService;
import com.walker.tx.common.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/account")
public class AccountInfoController {

    @Autowired
    AccountInfoService service;

    @PostMapping("/payment")
    public boolean orderPay(@RequestBody AccountDTO accountDTO) {
        return service.payment(accountDTO);
    }
}
