package com.walker.transaction;

import com.walker.transaction.user.service.ForeignTradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserTransApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTransAppTest
{
    @Autowired
    ForeignTradeService foreignTradeService;

    @Test
    public void usdTrade() {
        try {
            foreignTradeService.usdTrade(1,2,10);
        } catch (Exception e) {
        }
        System.out.println("执行完毕");
    }

    @Test
    public void rmbTrade() throws Exception {
        foreignTradeService.rmbTrade(1,2,7);
    }
}
