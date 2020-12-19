package com.walker.transaction.rmb;

/**
 * @author fcwalker
 * @date 2020/12/18 17:15
 **/
public interface RmbAccountService {

    /**
     * 人民币支付
     * @param payerId 付款方
     * @param amount   金额
     * @throws Exception
     */
    boolean rmbPayment(Integer payerId, Integer amount) throws Exception;

    /**
     * 人民币收款
     * @param payerId
     * @param amount
     * @throws Exception
     */
    boolean rmbCollection(Integer payerId, Integer amount) throws Exception;
}
