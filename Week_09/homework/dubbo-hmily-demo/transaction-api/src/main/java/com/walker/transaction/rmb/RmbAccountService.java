package com.walker.transaction.rmb;

/**
 * @author fcwalker
 * @date 2020/12/18 17:15
 **/
public interface RmbAccountService {

    /**
     * 美元兑换
     * @param payerId 付款方
     * @param payeeId 收款方
     */
    void rmbTransaction(Integer payerId, Integer payeeId);
}
