package com.walker.transaction.user.service;

/**
 * @author fcwalker
 * @date 2020/12/18 17:53
 **/
public interface ForeignTradeService {

    /**
     * 美元兑换人民币
     * @param payerId
     * @param payeeId
     * @param usdCount
     * @return
     */
    boolean usdTrade(Integer payerId, Integer payeeId, Integer usdCount) throws Exception;

    /**
     * 人民币兑换美元
     * @param payerId
     * @param payeeId
     * @param rmbCount
     * @return
     */
    boolean rmbTrade(Integer payerId, Integer payeeId, Integer rmbCount) throws Exception;
}
