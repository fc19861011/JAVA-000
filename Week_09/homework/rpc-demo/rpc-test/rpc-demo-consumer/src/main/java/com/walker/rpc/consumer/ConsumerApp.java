package com.walker.rpc.consumer;

import com.walker.core.proxy.client.RpcClientProxy;
import com.walker.rpc.common.entitiy.UserEntity;
import com.walker.rpc.common.service.UserService;

import java.util.List;

/**
 * @author fcwalker
 *
 */
//@SpringBootApplication
//@EntityScan("com.walker.rpc.common")
public class ConsumerApp {
    public static void main(String[] args) {
        UserService userService = RpcClientProxy.create(UserService.class, "http://127.0.0.1:8989/");
        List<UserEntity> users = userService.findAll();
        users.stream().forEach(user -> System.out.println(user.getId() + ":" + user.getName()));

        UserEntity userEntity = new UserEntity();
        userEntity.setName("张三");
        userService.save(userEntity);
    }
}
