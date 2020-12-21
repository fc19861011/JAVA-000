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
     * @throws Exception
     */
    @Hmily
    boolean rmbPayment(Integer payerId, Integer amount) throws Exception;

    /**
     * 人民币收款
     * @param payerId
     * @param amount
     * @throws Exception
     */
    @Hmily
    boolean rmbCollection(Integer payerId, Integer amount) throws Exception;
}
