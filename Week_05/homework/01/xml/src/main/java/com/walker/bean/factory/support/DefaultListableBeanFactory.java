package com.walker.bean.factory.support;

import com.walker.bean.factory.BeanFactory;
import com.walker.bean.factory.config.BeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dell
 * @date 2020/11/18 16:56
 **/
public class DefaultListableBeanFactory implements BeanFactory {
    private String serializationId;
    private final Map<Class<?>, Object> resolvableDependencies = new ConcurrentHashMap<>(16);
    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

    @Override
    public Object getBean(String name) {
        return resolvableDependencies.get(name);
    }
}
