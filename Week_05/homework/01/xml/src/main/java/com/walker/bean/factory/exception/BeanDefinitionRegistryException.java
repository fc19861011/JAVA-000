package com.walker.bean.factory.exception;

/**
 * Bean定义注册接口
 * BeanFactory的行为接口
 */
public class BeanDefinitionRegistryException extends RuntimeException {
    public BeanDefinitionRegistryException(String errorMsg) {
        super(errorMsg);
    }
}
