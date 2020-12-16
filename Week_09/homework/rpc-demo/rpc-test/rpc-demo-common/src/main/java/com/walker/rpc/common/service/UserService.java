package com.walker.rpc.common.service;

import com.walker.rpc.common.entitiy.UserEntity;

import java.util.List;

/**
 * @author fcwalker
 * @date 2020/12/16 17:04
 **/
public interface UserService {
    List<UserEntity> findAll();

    void save(UserEntity userEntity);
}
