package com.walker.transaction.usd;

import org.dromara.hmily.annotation.Hmily;

/**
 * @author fcwalker
 * @date 2020/12/18 17:15
 **/
public interface UsdAccountService {

    /**
     * 美元支付
     * @param payerId 付款方
     * @param amount   金额
     * @throws Exception s
     */
    @Hmily
    boolean usdPayment(Integer payerId, Integer amount) throws Exception;

    /**
     * 美元收款
     * @param payerId
     * @param amount
     * @throws Exception
     */
    @Hmily
    boolean usdCollection(Integer payerId, Integer amount) throws Exception;

    @Hmily
    boolean usdTrade(Integer payerId, Integer payeeId, Integer usdCount) throws Exception;
}
