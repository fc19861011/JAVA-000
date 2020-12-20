package com.walker.transaction.usd;

import com.walker.transaction.usd.service.ForeignTradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UsdTransApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTransAppTest
{
    @Autowired
    ForeignTradeService foreignTradeService;

    @Test
    public void usdTrade() throws Exception {
        foreignTradeService.usdTrade(1,2,1);
    }
}
