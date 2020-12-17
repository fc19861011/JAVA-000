package com.walker.rpc.service;

import com.walker.rpc.common.entitiy.UserEntity;
import com.walker.rpc.common.service.UserService;
import com.walker.rpc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fcwalker
 * @date 2020/12/16 17:08
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;

    @Override
    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(UserEntity userEntity) {
        repository.save(userEntity);
    }
}
