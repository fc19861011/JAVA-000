package com.walker.rpc.provider.resolver;

import com.walker.core.proxy.server.RpcServerResolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Arrays;

/**
 * 利用反射查找
 * 简单粗暴的从spring注册的bean中进行模拟代理的实现（理论上不应依赖spring的bean）
 * @author fcwalker
 * @date 2020/12/16 17:09
 **/
public class RpcResole implements RpcServerResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 服务注册时名称未写全
     * 
     * @param serviceClassName 服务提供类名
     * @return Object
     */
    @Override
    public Object resolve(final String serviceClassName) {
        return this.applicationContext
                .getBean(Arrays.stream(this.applicationContext.getBeanDefinitionNames())
                        .filter(bn -> compareCls(bn, serviceClassName))
                        .findFirst().orElse(null));
    }

    private boolean compareCls(final String beanName, final String serviceClassName) {
        Class<?> beanCls = this.applicationContext.getBean(beanName).getClass();
        String beanClsName = beanCls.getName();
        String defaultPackage = "com.walker";
        if (beanClsName.startsWith(defaultPackage)) {
            return Arrays.stream(beanCls.getInterfaces())
                    .anyMatch(interfaceCls -> interfaceCls.getName().equals(serviceClassName));
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
