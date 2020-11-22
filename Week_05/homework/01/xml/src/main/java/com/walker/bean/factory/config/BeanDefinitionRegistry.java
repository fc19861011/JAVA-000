package com.walker.bean.factory.config;

import com.walker.bean.factory.config.BeanDefinition;

/**
 * Bean定义注册接口
 * BeanFactory的行为接口
 */
public interface BeanDefinitionRegistry {
    /**
     * 注册bean定义信息
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 获取bean定义信息
     * @param beanName
     * @return
     */
    BeanDefinition getBeanDefinition(String beanName);

    /**
     * 根据beanName判断bean定义信息是否存在
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);
}
