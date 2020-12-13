package com.walker.distributed.transaction.order.client;

import com.sun.javafx.binding.StringFormatter;
import com.walker.tx.common.dto.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountHystrix implements AccountClient {

    @Override
    public Boolean payment(AccountDTO accountDO) {
        System.out.println(StringFormatter.format("执行断路器，账户参数：%s", accountDO.toString()));
        return false;
    }

}
