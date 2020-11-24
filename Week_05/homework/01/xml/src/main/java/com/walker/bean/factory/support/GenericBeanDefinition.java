package com.walker.bean.factory.support;

import com.walker.bean.factory.config.BeanDefinition;
import com.walker.bean.factory.config.BeanReference;
import lombok.Setter;

import java.util.List;

@Setter
public class GenericBeanDefinition implements BeanDefinition {
    Class<?> beanClass;
    String factoryBeanName;
    String factoryMethodName;
    String initMethodName;
    String destroyMethodName;
    String scope = BeanDefinition.SCOPE_SINGLETON;

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    boolean primary = false;
    List<?> constructorArgumentValues;

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
    public boolean isPrototype() {
        return BeanDefinition.SCOPE_PROTOTYPE.equals(getScope()) ? true : false;
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

    @Override
    public boolean isPrimary() {
        return this.primary;
    }

    public void setConstructorArgumentValues(List<?> constructorArgumentValues) {
        this.constructorArgumentValues = constructorArgumentValues;
    }

    @Override
    public List<?> getConstructorArgumentValues() {
        return this.constructorArgumentValues;
    }

    @Override
    public List<BeanReference> getBeanReferences() {
        return null;
    }

}
