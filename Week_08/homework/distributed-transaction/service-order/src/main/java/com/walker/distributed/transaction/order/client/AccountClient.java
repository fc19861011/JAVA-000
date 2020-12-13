package com.walker.distributed.transaction.order.client;

import com.walker.tx.common.dto.AccountDTO;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "account-service")
public interface AccountClient {

    /**
     * 用户账户付款.
     *
     * @param accountDO 实体类
     * @return true 成功
     */
    @RequestMapping("/account/payment")
    @Hmily
    Boolean payment(@RequestBody AccountDTO accountDO);
}
