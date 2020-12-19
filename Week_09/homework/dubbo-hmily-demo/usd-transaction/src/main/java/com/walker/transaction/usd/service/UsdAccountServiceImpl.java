package com.walker.transaction.usd.service;

import com.walker.transaction.usd.UsdAccount;
import com.walker.transaction.usd.UsdAccountFreeze;
import com.walker.transaction.usd.UsdAccountService;
import com.walker.transaction.usd.repository.UsdAccountFreezeRepository;
import com.walker.transaction.usd.repository.UsdAccountRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fcwalker
 * @date 2020/12/18 17:38
 **/
@Transactional(rollbackFor = Exception.class)
@DubboService(version = "1.0.0")
public class UsdAccountServiceImpl implements UsdAccountService {

    @Autowired
    private UsdAccountRepository usdAccountRepository;

    @Autowired
    private UsdAccountFreezeRepository usdAccountFreezeRepository;

    @Override
    @HmilyTCC(confirmMethod = "confirmUsdPayment", cancelMethod = "cancelUsdPayment")
    public boolean usdPayment(Integer payerId, Integer count) throws Exception {
        UsdAccount usdAccount = usdAccountRepository.getOne(payerId);
        Integer amount = usdAccount.getAmount();
        if (amount < count) {
            throw new Exception("余额不足");
        }
        UsdAccountFreeze accountFreeze = usdAccountFreezeRepository.getOne(payerId);
        int freezeCount = 0;
        if (null == accountFreeze) {
            accountFreeze = new UsdAccountFreeze();
            accountFreeze.setUserId(payerId);
            accountFreeze.setAmount(count);
            accountFreeze.setFreezeType(1);
            usdAccountFreezeRepository.save(accountFreeze);
            freezeCount = 1;
        } else {
            freezeCount = usdAccountFreezeRepository.accountFreeze(payerId, count, 1);
        }
        int payCount = usdAccountRepository.payment(payerId, count);
        if (payCount > 0 && freezeCount > 0) {
            return true;
        }
        throw new Exception("支付失败");
    }

    public void confirmUsdPayment(Integer payerId, Integer count) {
        usdAccountFreezeRepository.accountFinish(payerId, count, 1);
    }

    public void cancelUsdPayment(Integer payerId, Integer count) {
        usdAccountRepository.paymentCancel(payerId, count);
        usdAccountFreezeRepository.accountFinish(payerId, count, 1);
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmUsdCollection", cancelMethod = "cancelUsdCollection")
    public boolean usdCollection(Integer payerId, Integer count) throws Exception {
        UsdAccountFreeze accountFreeze = usdAccountFreezeRepository.getOne(payerId);
        int freezeCount = 0;
        if (null == accountFreeze) {
            accountFreeze = new UsdAccountFreeze();
            accountFreeze.setUserId(payerId);
            accountFreeze.setAmount(count);
            accountFreeze.setFreezeType(2);
            usdAccountFreezeRepository.save(accountFreeze);
            freezeCount = 1;
        } else {
            freezeCount = usdAccountFreezeRepository.accountFreeze(payerId, count, 2);
        }
        if (freezeCount > 0) {
            return true;
        }
        throw new Exception("支付失败");
    }

    public void confirmUsdCollection(Integer payerId, Integer count) {
        usdAccountRepository.collection(payerId, count);
        usdAccountFreezeRepository.accountFinish(payerId, count, 2);
    }

    public void cancelUsdCollection(Integer payerId, Integer count) {
        usdAccountFreezeRepository.accountFinish(payerId, count, 2);
    }
}
