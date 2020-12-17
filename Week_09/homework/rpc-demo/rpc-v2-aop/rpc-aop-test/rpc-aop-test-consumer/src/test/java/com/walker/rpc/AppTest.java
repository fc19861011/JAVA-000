package com.walker.rpc;

import static org.junit.Assert.assertTrue;

import com.walker.rpc.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsumerApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppTest
{
    @Autowired
    TestService testService;

    @Test
    public void test()
    {
        testService.test();
    }
}
