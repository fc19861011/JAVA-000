package com.walker.bean.factory.config;

/**
 * IOC注入对象的关系
 * @author F.C
 */
public class BeanReference {
    String beanName;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    Class type;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }


}
