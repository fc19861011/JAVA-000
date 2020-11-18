package com.walker.bean.factory.config;

import lombok.Data;

import java.util.Set;

/**
 * bean定义信息
 *
 * @author dell
 * @date 2020/11/18 16:17
 **/
@Data
public class BeanDefinition {
    private String beanId;
    private String classpath;
    Set<BeanDefinitionProps> props;
}
