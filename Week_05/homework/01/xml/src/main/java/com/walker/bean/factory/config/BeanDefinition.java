package com.walker.bean.factory.config;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
     * 是否是多例
     *
     * @return
     */
    boolean isPrototype();

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
     * 根据class类型获取bean对象时，
     * 如果有多个同类型的对象，
     * 优先使用isPrimary为true的对象。
     *
     * @return
     */
    boolean isPrimary();

    /**
     * 获取构造器参数信息列表
     *
     * @return
     */
    List<?> getConstructorArgumentValues();

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
