package com.walker.distributed.transaction.account.service;

import com.walker.distributed.transaction.account.domain.AccountInfoEntity;
import com.walker.distributed.transaction.account.reponsitory.AccountInfoRepository;
import com.walker.tx.common.dto.AccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    AccountInfoRepository repository;

    @Override
    public boolean saveOrderInfo(AccountInfoEntity order) {
        repository.save(order);
        return true;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean payment(AccountDTO accountDTO) {
        AccountInfoEntity entity = repository.findAccountByUserId(accountDTO.getUserId());
        entity.setFreezeAmount(accountDTO.getAmount());
        entity.setBalance(entity.getBalance().subtract(accountDTO.getAmount()));
        repository.save(entity);
        return true;
    }

    public boolean confirm(final AccountDTO accountDTO) {
        log.info("============执行confirm 付款接口===============");
        AccountInfoEntity entity = repository.findAccountByUserId(accountDTO.getUserId());
        entity.setFreezeAmount(BigDecimal.ZERO);
        repository.save(entity);
        return true;
    }


    /**
     * Cancel boolean.
     *
     * @param accountDTO the account dto
     * @return the boolean
     */
    public boolean cancel(final AccountDTO accountDTO) {
        log.info("============执行cancel 付款接口===============");
        AccountInfoEntity entity = repository.findAccountByUserId(accountDTO.getUserId());
        entity.setBalance(entity.getBalance().add(accountDTO.getAmount()));
        entity.setFreezeAmount(BigDecimal.ZERO);
        repository.save(entity);
        return true;
    }
}
