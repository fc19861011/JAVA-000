package com.walker.shardingjdbc.service;


import com.walker.shardingjdbc.domain.TestEnity;

import java.util.List;


public interface TestService {

    void saveTestEntity(TestEnity testEnity);

    List<TestEnity> queryTestEntitys();

}
