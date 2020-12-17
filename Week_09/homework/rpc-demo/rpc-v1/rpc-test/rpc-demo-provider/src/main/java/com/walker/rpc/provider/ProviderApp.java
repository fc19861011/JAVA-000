package com.walker.rpc.provider;

import com.walker.core.proxy.server.RpcServerInvoker;
import com.walker.rpc.provider.resolver.RpcResole;
import com.walker.rpc.provider.resolver.RpcResoleByClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * @author fcwalker
 *
 */
@SpringBootApplication
@EntityScan("com.walker.rpc.common")
public class ProviderApp {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApp.class);
    }

    @Bean(name = "invokerByReflex")
    public RpcServerInvoker serverInvoker() {
        return new RpcServerInvoker(resole());
    }

    @Bean(name = "invokerByServiceCls")
    public RpcServerInvoker serverInvokerByCls() {
        return new RpcServerInvoker(resoleByCls());
    }

    @Bean
    public RpcResole resole() {
        return  new RpcResole();
    }

    @Bean
    public RpcResoleByClass resoleByCls() {
        return  new RpcResoleByClass();
    }
}
