package com.walker.transaction.rmb.service;

import com.walker.transaction.rmb.RmbAccount;
import com.walker.transaction.rmb.RmbAccountFreeze;
import com.walker.transaction.rmb.RmbAccountService;
import com.walker.transaction.rmb.repository.RmbAccountFreezeRepository;
import com.walker.transaction.rmb.repository.RmbAccountRepository;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fcwalker
 * @date 2020/12/18 17:28
 **/
@DubboService(version = "1.0.0")
@Service
@Transactional(rollbackFor = Exception.class)
public class RmbAccountServiceImpl implements RmbAccountService {

    @Autowired
    RmbAccountRepository rmbAccountRepository;

    @Autowired
    RmbAccountFreezeRepository rmbAccountFreezeRepository;

    @Override
    @HmilyTCC(confirmMethod = "confirmRmbPayment", cancelMethod = "cancelRmbPayment")
    public boolean rmbPayment(Integer payerId, Integer count, Long transId) throws Exception {
        RmbAccount rmbAccount = rmbAccountRepository.getOne(payerId);
        Integer amount = rmbAccount.getAmount();
        if (amount < count) {
            throw new Exception("余额不足");
        }
        RmbAccountFreeze accountFreeze = rmbAccountFreezeRepository.findByUserIdAndFreezeType(payerId, transId);
        int freezeCount = 0;
        if (null == accountFreeze) {
            accountFreeze = new RmbAccountFreeze();
            accountFreeze.setUserId(payerId);
            accountFreeze.setAmount(count);
            accountFreeze.setFreezeType(transId);
            rmbAccountFreezeRepository.save(accountFreeze);
            freezeCount = 1;
        } else {
            freezeCount = rmbAccountFreezeRepository.accountFreeze(payerId, count, transId);
        }
        int paycount = rmbAccountRepository.payment(payerId, count);
        if (paycount > 0 && freezeCount > 0) {
            return true;
        }
        throw new Exception("人民币支付失败");
    }

    public void confirmRmbPayment(Integer payerId, Integer count, Long transId) {
        System.out.println("【" + payerId + "】支付 (￥" + count + ") 成功");
        rmbAccountFreezeRepository.accountFinish(payerId, count, transId);
    }

    public void cancelRmbPayment(Integer payerId, Integer count, Long transId) {
        System.out.println("【" + payerId + "】支付 (￥" + count + ") cancel");
        RmbAccountFreeze accountFreeze = rmbAccountFreezeRepository.findByUserIdAndFreezeType(payerId, transId);
        if(accountFreeze == null || accountFreeze.getAmount() == 0) {
            return;
        }
        rmbAccountRepository.paymentCancel(payerId, count);
        rmbAccountFreezeRepository.accountFinish(payerId, count, transId);
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmRmbCollection", cancelMethod = "cancelRmbCollection")
    public boolean rmbCollection(Integer payerId, Integer count, Long transId) throws Exception {
        RmbAccountFreeze accountFreeze = rmbAccountFreezeRepository.findByUserIdAndFreezeType(payerId, transId);
        int freezeCount = 0;
        if (null == accountFreeze) {
            accountFreeze = new RmbAccountFreeze();
            accountFreeze.setUserId(payerId);
            accountFreeze.setAmount(count);
            accountFreeze.setFreezeType(transId);
            rmbAccountFreezeRepository.save(accountFreeze);
            freezeCount = 1;
        } else {
            freezeCount = rmbAccountFreezeRepository.accountFreeze(payerId, count, transId);
        }
        if (freezeCount > 0) {
            return true;
        }
        throw new Exception("人民币收款失败");
    }

    public void confirmRmbCollection(Integer payerId, Integer count, Long transId) {
        System.out.println("【" + payerId + "】收款 (￥" + count + ") 成功");
        rmbAccountRepository.collection(payerId, count);
        rmbAccountFreezeRepository.accountFinish(payerId, count, transId);
    }

    public void cancelRmbCollection(Integer payerId, Integer count, Long transId) {
        System.out.println("【" + payerId + "】收款 (￥" + count + ") cancel");
        rmbAccountFreezeRepository.accountFinish(payerId, count, transId);
    }
}
