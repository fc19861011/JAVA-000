package com.walker.rws.service;

import com.walker.rws.domain.TestEnity;

import java.util.List;

public interface TestService {

    void saveTestEntity(TestEnity testEnity);

    List<TestEnity> queryTestEntitys();

}
