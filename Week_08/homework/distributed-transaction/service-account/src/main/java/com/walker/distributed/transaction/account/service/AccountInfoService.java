package com.walker.distributed.transaction.account.service;

import com.walker.distributed.transaction.account.domain.AccountInfoEntity;
import com.walker.tx.common.dto.AccountDTO;

public interface AccountInfoService {

    boolean saveOrderInfo(AccountInfoEntity order);

    boolean payment(AccountDTO accountDTO);
}
