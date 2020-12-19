package com.walker.transaction.usd;

/**
 * @author fcwalker
 * @date 2020/12/18 17:15
 **/
public interface UsdAccountFreezeService {

    /**
     * 冻结用户的美元
     * @param userId 冻结用户
     * @param amount  冻结数量
     * @return boolean
     */
    boolean freezeUsdAccount(Integer userId, Integer amount);
}
