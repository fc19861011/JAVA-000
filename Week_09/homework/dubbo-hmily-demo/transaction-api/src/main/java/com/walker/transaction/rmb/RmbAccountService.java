package com.walker.transaction.rmb;

import org.dromara.hmily.annotation.Hmily;

/**
 * @author fcwalker
 * @date 2020/12/18 17:15
 **/
public interface RmbAccountService {

    /**
     * 人民币支付
     * @param payerId 付款方
     * @param amount   金额
     * @return boolean
     * @throws Exception
     */
    @Hmily
    boolean rmbPayment(Integer payerId, Integer amount, Long transId) throws Exception;

    /**
     * 人民币收款
     * @param payerId
     * @param amount
     * @return boolean
     * @throws Exception
     */
    @Hmily
    boolean rmbCollection(Integer payerId, Integer amount, Long transId) throws Exception;
}
