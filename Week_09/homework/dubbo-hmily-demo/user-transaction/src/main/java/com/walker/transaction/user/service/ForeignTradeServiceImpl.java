package com.walker.transaction.user.service;

import com.walker.transaction.rmb.RmbAccountFreezeService;
import com.walker.transaction.rmb.RmbAccountService;
import com.walker.transaction.usd.UsdAccountService;
import org.springframework.stereotype.Service;

/**
 * @author fcwalker
 * @date 2020/12/18 17:54
 **/
@Service
public class ForeignTradeServiceImpl implements ForeignTradeService {

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
    private RmbAccountService rmbAccountService;

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
    private UsdAccountService usdAccountService;



}
