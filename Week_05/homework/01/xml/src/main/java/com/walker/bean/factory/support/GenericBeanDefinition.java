package com.walker.bean.factory.support;

import com.walker.bean.factory.config.BeanDefinition;
import lombok.Setter;

@Setter
public class GenericBeanDefinition implements BeanDefinition {
    Class<?> beanClass;
    String factoryBeanName;
    String factoryMethodName;
    String initMethodName;
    String destroyMethodName;
    String scope;

    @Override
    public Class<?> getBeanClass() {
        return this.beanClass;
    }

    @Override
    public String getScope() {
        return this.scope;
    }

    @Override
    public boolean isSingleton() {
        return BeanDefinition.SCOPE_SINGLETON.equals(getScope()) ? true : false;
    }

    @Override
    public String getFactoryBeanName() {
        return this.factoryBeanName;
    }

    @Override
    public String getFactoryMethodName() {
        return this.factoryMethodName;
    }

    @Override
    public String getInitMethodName() {
        return this.initMethodName;
    }

    @Override
    public String getDestroyMethodName() {
        return this.destroyMethodName;
    }

}
