package com.walker.rpc.aop.client;

import com.walker.rpc.aop.annotation.RpcReference;
import com.walker.rpc.aop.client.RpcClientProxy;
import com.walker.rpc.aop.client.RpcProxyScanPackages;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 动态注册带有@RpcReference注解的对象
 *
 * @author fcwalker
 * @date 2020/12/17 18:15
 **/
@Component
public class RpcServiceInstantiation implements ApplicationRunner, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        RpcProxyScanPackages.clsRpcReferences.keySet()
        .forEach(cls -> {
            Map<Field, RpcReference> fieldReferenceMap = RpcProxyScanPackages.clsRpcReferences.get(cls);
            Object target = applicationContext.getBean(cls);
            for(Field field : fieldReferenceMap.keySet()) {
                RpcReference reference = fieldReferenceMap.get(field);
                String fieldName = field.getName();
                String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
                Class<?> fieldCls = field.getType();
                Method m;
                try {
                    m = cls.getMethod(methodName, fieldCls);
                    m.invoke(target, RpcClientProxy.create(fieldCls, reference.url()));
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
