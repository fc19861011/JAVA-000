package com.walker.shardingjdbc.service;

import com.walker.shardingjdbc.domain.TestEnity;
import com.walker.shardingjdbc.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestRepository testRepository;

    @Override
    public void saveTestEntity(TestEnity testEnity) {
        testRepository.save(testEnity);
    }

    @Override
    public List<TestEnity> queryTestEntitys() {
        List<TestEnity> enities = testRepository.findAll();
        return enities;
    }
}
