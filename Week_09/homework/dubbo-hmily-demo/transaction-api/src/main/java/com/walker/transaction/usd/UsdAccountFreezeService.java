package com.walker.transaction.usd;

/**
 * @author fcwalker
 * @date 2020/12/18 17:15
 **/
public interface UsdAccountFreezeService {

    /**
     * 冻结用户的美元
     * @param userid 冻结用户
     * @param count  冻结数量
     */
    void freezeUsdAccount(Integer userid, Integer count);
}
