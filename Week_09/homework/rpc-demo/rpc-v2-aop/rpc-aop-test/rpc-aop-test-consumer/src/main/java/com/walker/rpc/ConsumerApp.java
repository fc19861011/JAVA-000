package com.walker.rpc;

import com.walker.rpc.aop.annotation.RpcClientScan;
import com.walker.rpc.aop.annotation.RpcReference;
import com.walker.rpc.aop.client.RpcClientProxy;
import com.walker.rpc.common.entitiy.UserEntity;
import com.walker.rpc.common.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * @author fcwalker
 */
@SpringBootApplication
@RpcClientScan("com.walker.rpc.service")
public class ConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class);
    }
}
