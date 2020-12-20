package com.walker.transaction.usd.service;

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

}
