package com.walker.rpc.aop.server;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 根据class类型进行查找
 * spring bean注册时，会按照bean类型、bean父类类型、bean继承接口的类型进行注册
 * @author fcwalker
 * @date 2020/12/16 17:09
 **/
public class RpcResoleByClass implements RpcServerResolver, ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 服务注册时名称未写全
     * 
     * @param serviceClassName 服务提供类名
     * @return Object
     */
    @Override
    public Object resolve(final String serviceClassName) {
        Class<?> cls = null;
        try {
            cls = Class.forName(serviceClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return this.applicationContext.getBean(cls);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
