package com.walker.rpc.provider.resolver;

import com.walker.core.proxy.server.RpcServerResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;

/**
 * @author fcwalker
 * @date 2020/12/16 17:09
 **/
public class RpcResole implements RpcServerResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final String defaultPackage = "com.walker";

    /**
     * 服务注册时名称未写全
     * 
     * @param serviceClassName
     * @return
     */
    @Override
    public Object resolve(final String serviceClassName) {
        return this.applicationContext
                .getBean(Arrays.asList(this.applicationContext.getBeanDefinitionNames())
                        .stream()
                        .filter(bn -> compareCls(bn, serviceClassName))
                        .findFirst().get());
    }

    boolean compareCls(final String beanName, final String serviceClassName) {
        Class<?> beanCls = this.applicationContext.getBean(beanName).getClass();
        String beanClsName = beanCls.getName();
        if (beanClsName.startsWith(defaultPackage)) {
            return Arrays.asList(beanCls.getInterfaces()).stream()
                    .anyMatch(interfaceCls -> interfaceCls.getName().equals(serviceClassName));
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
