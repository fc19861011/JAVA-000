package com.walker.bean.factory.config;

import org.apache.commons.lang3.StringUtils;

/**
 * bean定义信息接口
 *
 * @author dell
 * @date 2020/11/18 16:17
 **/
public interface BeanDefinition {
    /**
     * 单例模式
     */
    String SCOPE_SINGLETON = "singleton";
    /**
     * 多例模式
     */
    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 获取bean对象的class
     *
     * @return
     */
    Class<?> getBeanClass();

    /**
     * @return
     */
    String getScope();

    /**
     * 是否是单例
     *
     * @return
     */
    boolean isSingleton();

    /**
     * 工厂beanName
     *
     * @return
     */
    String getFactoryBeanName();

    /**
     * 工厂方法名
     *
     * @return
     */
    String getFactoryMethodName();

    /**
     * 初始化方法
     *
     * @return
     */
    String getInitMethodName();

    /**
     * 销毁方法
     *
     * @return
     */
    String getDestroyMethodName();


    /**
     * 校验bean定义的合法性
     *
     * @return
     */
    default boolean validate() {
        // 没有定义class，工厂bean或工厂方法没指定，不合法
        if (this.getBeanClass() == null) {
            if (StringUtils.isBlank(getFactoryBeanName()) || StringUtils.isBlank(getFactoryMethodName())) {
                return false;
            }
        }

        // 定义了class，有定义了工厂bean，不合法
        if (this.getBeanClass() != null && StringUtils.isNotBlank(getFactoryBeanName())) {
            return false;
        }
        return true;
    }


}
