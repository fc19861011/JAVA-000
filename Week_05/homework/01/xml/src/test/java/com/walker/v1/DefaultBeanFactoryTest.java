package com.walker.v1;


import com.walker.bean.factory.config.BeanDefinition;
import com.walker.bean.factory.support.DefaultBeanFactory;
import com.walker.bean.factory.support.GenericBeanDefinition;
import com.walker.example.BeanTest;
import com.walker.example.FactoryBeanTest;
import org.junit.AfterClass;
import org.junit.Test;

public class DefaultBeanFactoryTest {
    static DefaultBeanFactory dbf = new DefaultBeanFactory();

    @Test
    public void testRegistBeanDefinition() throws Exception {

        GenericBeanDefinition bd = new GenericBeanDefinition();

        bd.setBeanClass(BeanTest.class);
        bd.setScope(BeanDefinition.SCOPE_SINGLETON);

        bd.setInitMethodName("init");
        bd.setDestroyMethodName("destroy");

        dbf.registerBeanDefinition("beantest", bd);
        System.out.println("=======================testRegistBeanDefinition");
    }

    @Test
    public void testRegistStaticFactoryMethod() throws Exception {

        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(FactoryBeanTest.class);
        bd.setFactoryMethodName("getBean1");

        dbf.registerBeanDefinition("staticbeantest", bd);
        System.out.println("=======================testRegistStaticFactoryMethod");
    }

    @Test
    public void testRegistFactoryMethod() throws Exception {

        GenericBeanDefinition bd = new GenericBeanDefinition();
        bd.setBeanClass(FactoryBeanTest.class);
        String fbname = "factory";
        dbf.registerBeanDefinition(fbname, bd);

        bd = new GenericBeanDefinition();
        bd.setFactoryBeanName(fbname);
        bd.setFactoryMethodName("getBean2");
        bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        dbf.registerBeanDefinition("factoryBean", bd);
        System.out.println("=======================testRegistFactoryMethod");
    }

    @AfterClass
    public static void testGetBean() throws Exception {

        System.out.println("构造方法");
        for (int i = 0; i < 3; i++) {
            BeanTest beanTest = (BeanTest) dbf.getBean("beantest");
            beanTest.doSomthing();
        }

        System.out.println("静态工厂方法");
        for (int i = 0; i < 3; i++) {
            BeanTest beanTest = (BeanTest) dbf.getBean("staticbeantest");
            beanTest.doSomthing();
        }

        System.out.println("工厂方法方式");
        for (int i = 0; i < 3; i++) {
            BeanTest beanTest = (BeanTest) dbf.getBean("factoryBean");
            beanTest.doSomthing();
        }

        dbf.close();
    }
}

