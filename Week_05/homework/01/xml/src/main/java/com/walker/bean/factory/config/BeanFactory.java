package com.walker.bean.factory.config;

/**
 * @author dell
 * @date 2020/11/18 16:51
 **/
public interface BeanFactory {
    Object getBean(String beanName) throws Exception;
}
