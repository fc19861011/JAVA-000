package com.walker.bean.factory.config;

import java.util.Map;

/**
 * @author dell
 * @date 2020/11/18 16:51
 **/
public interface BeanFactory {
    /**
     * 根据beanName获取bean的实例
     * @param beanName
     * @return
     * @throws Exception
     */
    Object getBean(String beanName) throws Exception;


    /**
     * 根据beanName获取bean对象的class类型
     * @param beanName
     * @return
     * @throws Exception
     */
    Class<?> getType(String beanName) throws  Exception;

    /**
     * 根据bean对象类型获取bean实例
     * @param beanClass
     * @param <T>
     * @return
     * @throws Exception
     */
    <T>T getBean(Class<T> beanClass) throws Exception;

    /**
     * 根据bean对象类型获取该类型的所有bean
     * @param beanClass
     * @param <T>
     * @return
     * @throws Exception
     */
    <T> Map<String, T> getBeansOfType(Class<T> beanClass) throws Exception;
}
