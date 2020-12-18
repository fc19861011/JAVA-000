package com.walker.transaction.usd;

/**
 * @author fcwalker
 * @date 2020/12/18 17:15
 **/
public interface UsdAccountService {

    /**
     * 人民币兑换
     * @param payerId 付款方
     * @param payeeId 收款方
     */
    void usdTransaction(Integer payerId, Integer payeeId);
}
