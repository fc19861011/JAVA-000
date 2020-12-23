package com.walker.transaction.usd.service;

import com.walker.transaction.usd.UsdAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fcwalker
 * @date 2020/12/18 17:54
 **/
@Service
public class ForeignTradeServiceImpl implements ForeignTradeService {

    @Autowired
    private UsdAccountService usdAccountService;

    @Override
    public boolean usdTrade(Integer payerId, Integer payeeId, Integer usdCount) throws Exception {
        // 兑换人支付美元
//        usdAccountService.usdTrade(payerId, payeeId, usdCount);
        return true;
    }

}
