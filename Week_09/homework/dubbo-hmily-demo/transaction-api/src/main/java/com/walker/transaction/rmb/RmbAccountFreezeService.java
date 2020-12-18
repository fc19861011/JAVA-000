package com.walker.transaction.rmb;

/**
 * @author fcwalker
 * @date 2020/12/18 17:15
 **/
public interface RmbAccountFreezeService {

    /**
     * 冻结人民币
     * @param userid 冻结用户
     * @param count  冻结数量
     */
    void freezeRmbAccount(Integer userid, Integer count);
}
