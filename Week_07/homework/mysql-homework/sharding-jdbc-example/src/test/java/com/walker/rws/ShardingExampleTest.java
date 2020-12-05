package com.walker.rws;

import com.walker.shardingjdbc.ShardingExampleApp;
import com.walker.shardingjdbc.domain.TestEnity;
import com.walker.shardingjdbc.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Unit test for simple DynamicDataSourceApp.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingExampleApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShardingExampleTest
{
    @Autowired
    TestService service;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void saveData()
    {
        TestEnity testEnity = new TestEnity();
//        testEnity.setId(0);
        testEnity.setName("test");
        service.saveTestEntity(testEnity);
    }

    @Test
    public void queryData() {
        List<TestEnity> testEnities = service.queryTestEntitys();
        System.out.println(testEnities.size());
    }
}
