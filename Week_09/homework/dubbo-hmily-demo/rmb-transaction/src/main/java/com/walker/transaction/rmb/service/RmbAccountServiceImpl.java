package com.walker.transaction.rmb.service;

import com.walker.transaction.rmb.RmbAccount;
import com.walker.transaction.rmb.RmbAccountFreeze;
import com.walker.transaction.rmb.RmbAccountService;
import com.walker.transaction.rmb.repository.RmbAccountFreezeRepository;
import com.walker.transaction.rmb.repository.RmbAccountRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fcwalker
 * @date 2020/12/18 17:28
 **/
@Transactional(rollbackFor = Exception.class)
@DubboService(version = "1.0.0")
public class RmbAccountServiceImpl implements RmbAccountService {

    @Autowired
    RmbAccountRepository rmbAccountRepository;

    @Autowired
    RmbAccountFreezeRepository rmbAccountFreezeRepository;

    @Override
    @HmilyTCC(confirmMethod = "confirmRmbPayment", cancelMethod = "cancelRmbPayment")
    public boolean rmbPayment(Integer payerId, Integer count) throws Exception {
        RmbAccount rmbAccount = rmbAccountRepository.getOne(payerId);
        Integer amount = rmbAccount.getAmount();
        if (amount < count) {
            throw new Exception("余额不足");
        }
        RmbAccountFreeze accountFreeze = rmbAccountFreezeRepository.getOne(payerId);
        int freezeCount = 0;
        if (null == accountFreeze) {
            accountFreeze = new RmbAccountFreeze();
            accountFreeze.setUserId(payerId);
            accountFreeze.setAmount(count);
            accountFreeze.setFreezeType(1);
            rmbAccountFreezeRepository.save(accountFreeze);
            freezeCount = 1;
        } else {
            freezeCount = rmbAccountFreezeRepository.accountFreeze(payerId, count, 1);
        }
        int paycount = rmbAccountRepository.payment(payerId, count);
        if (paycount > 0 && freezeCount > 0) {
            return true;
        }
        throw new Exception("支付失败");
    }

    public void confirmRmbPayment(Integer payerId, Integer count) {
        rmbAccountFreezeRepository.accountFinish(payerId, count, 1);
    }

    public void cancelRmbPayment(Integer payerId, Integer count) {
        rmbAccountRepository.paymentCancel(payerId, count);
        rmbAccountFreezeRepository.accountFinish(payerId, count, 1);
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmRmbCollection", cancelMethod = "cancelRmbCollection")
    public boolean rmbCollection(Integer payerId, Integer count) throws Exception {
        RmbAccountFreeze accountFreeze = rmbAccountFreezeRepository.getOne(payerId);
        int freezeCount = 0;
        if (null == accountFreeze) {
            accountFreeze = new RmbAccountFreeze();
            accountFreeze.setUserId(payerId);
            accountFreeze.setAmount(count);
            accountFreeze.setFreezeType(2);
            rmbAccountFreezeRepository.save(accountFreeze);
            freezeCount = 1;
        } else {
            freezeCount = rmbAccountFreezeRepository.accountFreeze(payerId, count, 2);
        }
        if (freezeCount > 0) {
            return true;
        }
        throw new Exception("支付失败");
    }

    public void confirmRmbCollection(Integer payerId, Integer count) {
        rmbAccountRepository.collection(payerId, count);
        rmbAccountFreezeRepository.accountFinish(payerId, count, 2);
    }

    public void cancelRmbCollection(Integer payerId, Integer count) {
        rmbAccountFreezeRepository.accountFinish(payerId, count, 2);
    }
}
