package com.walker.transaction.rmb;

/**
 * @author fcwalker
 * @date 2020/12/18 17:15
 **/
public interface RmbAccountFreezeService {

    /**
     * 冻结人民币
     * @param userId 冻结用户
     * @param amount  冻结数量
     * @return boolean
     */
    boolean freezeRmbAccount(Integer userId, Integer amount);
}
