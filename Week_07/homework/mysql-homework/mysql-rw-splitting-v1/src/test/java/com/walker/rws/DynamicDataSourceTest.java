package com.walker.rws;

import com.walker.rws.domain.TestEnity;
import com.walker.rws.service.TestService;
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
@SpringBootTest(classes = DynamicDataSourceApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DynamicDataSourceTest {

    @Autowired
    TestService service;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void saveData() {
        TestEnity testEnity = new TestEnity();
//        testEnity.setId(0);
        testEnity.setName("test");
        service.saveTestEntity(testEnity);
    }

    @Test
    public void queryData() {
        List<TestEnity> testEnities = service.queryTestEntitys();
        testEnities.forEach(testEnity -> System.out.println(testEnity.getName())
        );

        testEnities = service.queryTestEntitys();
        testEnities.forEach(testEnity -> System.out.println(testEnity.getName())
        );
    }
}
