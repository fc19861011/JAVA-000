package com.walker.rpc.service;

import com.walker.rpc.aop.annotation.RpcReference;
import com.walker.rpc.aop.client.RpcClientProxy;
import com.walker.rpc.common.entitiy.UserEntity;
import com.walker.rpc.common.service.UserService;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fcwalker
 * @date 2020/12/17 23:31
 **/
@Service
@Setter
public class TestServiceImpl implements TestService {

    @RpcReference(url = "http://127.0.0.1:8989/")
    UserService userService;


    @Override
    public void test() {
        List<UserEntity> users = userService.findAll();
        users.stream().forEach(user -> System.out.println(user.getId() + ":" + user.getName()));
        UserEntity userEntity = new UserEntity();
        userEntity.setName("张三");
        userService.save(userEntity);
    }
}
