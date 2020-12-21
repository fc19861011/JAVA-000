package com.walker.transaction.web;

import com.walker.transaction.user.service.ForeignTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fcwalker
 * @date 2020/12/21 17:09
 **/
@RestController
public class UserController {
    @Autowired
    ForeignTradeService foreignTradeService;

    @GetMapping("/usdTrade")
    public void usdTrade() throws Exception {
        foreignTradeService.usdTrade(1,2,10);
    }
}
