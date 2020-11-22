package com.walker.example;

public class FactoryBeanTest {

    public static BeanTest getBean1() {
        return new BeanTest();
    }

    public BeanTest getBean2() {
        return new BeanTest();
    }
}
