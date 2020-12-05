package com.walker.rws.service;

import com.walker.rws.aop.DynamicDataSource;
import com.walker.rws.domain.TestEnity;
import com.walker.rws.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestRepository testRepository;

    @Override
    @DynamicDataSource
    public void saveTestEntity(TestEnity testEnity) {
        testRepository.save(testEnity);
    }

    @Override
    @DynamicDataSource
    public List<TestEnity> queryTestEntitys() {
        List<TestEnity> enities = testRepository.findAll();
        return enities;
    }
}
