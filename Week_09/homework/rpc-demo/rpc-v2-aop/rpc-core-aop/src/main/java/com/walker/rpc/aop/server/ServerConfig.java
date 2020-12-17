package com.walker.rpc.aop.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fcwalker
 * @date 2020/12/17 20:50
 **/
@Configuration
public class ServerConfig {
    @Bean
    public RpcServerInvoker serverInvokerByCls() {
        return new RpcServerInvoker(resoleByCls());
    }

    @Bean
    public RpcResoleByClass resoleByCls() {
        return  new RpcResoleByClass();
    }
}
