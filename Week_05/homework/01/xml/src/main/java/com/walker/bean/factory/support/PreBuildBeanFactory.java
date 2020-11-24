package com.walker.bean.factory.support;

import com.walker.annotation.Bean;
import com.walker.bean.factory.config.BeanDefinition;
import com.walker.bean.factory.config.BeanDefinitionRegistry;
import com.walker.bean.factory.config.BeanFactory;
import com.walker.bean.factory.exception.BeanDefinitionRegistryException;
import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dell
 * @date 2020/11/18 16:56
 * <p>
 * 继承DefaultBeanFactory
 * 实现如下内容：
 * 1、提前一次性初始化单例bean
 **/
public class PreBuildBeanFactory extends DefaultBeanFactory {
    public void preInstantiateSingletons() throws Exception {
        synchronized (this.beanDefinitionMap) {
            for (Map.Entry<String, BeanDefinition> entry : this.beanDefinitionMap.entrySet()) {
                String name = entry.getKey();
                BeanDefinition bd = entry.getValue();
                if (bd.isSingleton()) {
                    this.getBean(name);
                    System.out.println("preInstantiate：name=" + name + " " + bd);

                }
            }
        }
    }

}
