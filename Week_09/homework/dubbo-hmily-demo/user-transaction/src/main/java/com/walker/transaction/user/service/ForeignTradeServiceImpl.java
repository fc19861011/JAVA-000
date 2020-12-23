package com.walker.transaction.user.service;

import cn.hutool.core.util.RandomUtil;
import com.walker.transaction.Constant;
import com.walker.transaction.rmb.RmbAccountService;
import com.walker.transaction.usd.UsdAccountService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.core.holder.HmilyTransactionHolder;
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
        Long transId = RandomUtil.randomLong();
//        System.out.println("正在执行：美元兑换，全局事务id："+transId);
        // 兑换人支付美元
        usdAccountService.usdPayment(payerId, usdCount, transId);
        Integer rmbCount = usdCount * Constant.USD_RATE;
        // 被兑换人支付人民币
        rmbAccountService.rmbPayment(payeeId, rmbCount, transId);
        // 兑换人收取人民币
        rmbAccountService.rmbCollection(payerId, rmbCount, transId);
        // 被兑换人收取美元
        usdAccountService.usdCollection(payeeId, usdCount, transId);
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
        Long transId = HmilyTransactionHolder.getInstance().getCurrentTransaction().getTransId();
        System.out.println("正在执行：美元兑换，全局事务id："+transId);
        // 兑换人支付人民币
        rmbAccountService.rmbPayment(payerId, rmbCount, transId);
        Integer usdCount = rmbCount / Constant.USD_RATE;
        // 被兑换人支付美元
        usdAccountService.usdPayment(payeeId, usdCount, transId);
        // 兑换人收取美元
        usdAccountService.usdCollection(payerId, usdCount, transId);
        // 被兑换人收取人民币
        rmbAccountService.rmbCollection(payeeId, rmbCount, transId);
        return true;
    }
}
