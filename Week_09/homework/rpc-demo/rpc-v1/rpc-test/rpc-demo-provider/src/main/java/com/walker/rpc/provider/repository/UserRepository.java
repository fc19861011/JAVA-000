package com.walker.rpc.provider.repository;

import com.walker.rpc.common.entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dell
 * @date 2020/12/16 17:01
 **/
public interface UserRepository extends JpaRepository<UserEntity, Integer> {}
