package com.walker.transaction.user.service;

import com.walker.transaction.Constant;
import com.walker.transaction.rmb.RmbAccountService;
import com.walker.transaction.usd.UsdAccountService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author fcwalker
 * @date 2020/12/18 17:54
 **/
@Service
@Transactional
public class ForeignTradeServiceImpl implements ForeignTradeService {

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12390", timeout = 1000000)
    private RmbAccountService rmbAccountService;

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12391", timeout = 1000000)
    private UsdAccountService usdAccountService;

    @Override
    @HmilyTCC
    public boolean usdTrade(Integer payerId, Integer payeeId, Integer usdCount)  throws Exception {
        // 兑换人支付美元
        usdAccountService.usdPayment(payerId, usdCount);
        Integer rmbCount = usdCount * Constant.USD_RATE;
        // 被兑换人支付人民币
        rmbAccountService.rmbPayment(payeeId, rmbCount);
        // 兑换人收取人民币
        rmbAccountService.rmbCollection(payerId, rmbCount);
        // 被兑换人收取美元
        usdAccountService.usdCollection(payeeId, usdCount);
        return true;
    }

    public boolean confirmUsdTrade(Integer payerId, Integer payeeId, Integer usdCount) {
        System.out.println("兑换成功");
        return true;
    }

    public boolean cancelUsdTrade(Integer payerId, Integer payeeId, Integer usdCount) {
        System.out.println("兑换失败");
        return true;
    }

    @Override
    @HmilyTCC
    public boolean rmbTrade(Integer payerId, Integer payeeId, Integer rmbCount) throws Exception {
        // 兑换人支付人民币
        rmbAccountService.rmbPayment(payerId, rmbCount);
        Integer usdCount = rmbCount / Constant.USD_RATE;
        // 被兑换人支付美元
        usdAccountService.usdPayment(payeeId, usdCount);
        // 兑换人收取美元
        usdAccountService.usdCollection(payerId, usdCount);
        // 被兑换人收取人民币
        rmbAccountService.rmbCollection(payeeId, rmbCount);
        return true;
    }
}
