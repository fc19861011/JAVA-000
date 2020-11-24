package com.walker.bean.factory.support;

import com.walker.bean.factory.config.BeanDefinition;
import com.walker.bean.factory.config.BeanDefinitionRegistry;
import com.walker.bean.factory.config.BeanFactory;
import com.walker.bean.factory.exception.BeanDefinitionRegistryException;
import org.apache.commons.lang3.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dell
 * @date 2020/11/18 16:56
 *       <p>
 *       实现如下内容： 1、实现定义信息注册 2、实现bean工厂 3、实现单例 4、实现容器关闭时执行单例的销毁方法
 **/
public class DefaultBeanFactory implements BeanFactory, BeanDefinitionRegistry, Closeable {
    /**
     * beanDefinition的集合( [key | beanName] - [value | BeanDefinition] )
     */
    Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    /**
     * 单例的对象集合( [key | beanName] - [value | bean实例] )
     */
    Map<String, Object> singletonBeanMap = new ConcurrentHashMap<>(255);
    /**
     * 类类型与beanName对应关系的集合( [key | bean对象类型] - [value | beanNames] )
     */
    Map<Class<?>, Set<String>> typeMap = new ConcurrentHashMap<>(255);

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        /**
         * 需要思考的问题 1、如何存储BeanDefinition？ Map<String, BeanDefinition> 2、beanName重复如何处理？ - 允许重名，可以覆盖 -
         * 不允许重名，直接抛异常 （spring默认抛异常，可通过参数：spring.main.allow-bean-definition-overriding:true；来允许覆盖）
         * 3、这里需要做什么
         */
        Objects.requireNonNull(beanName, "注册bean需要给入beanName");
        Objects.requireNonNull(beanDefinition, "注册bean需要给入beanDefinition");
        if (!beanDefinition.validate()) {
            throw new BeanDefinitionRegistryException(
                    "名字为[" + beanName + "]的bean定义不合法：" + beanDefinition.toString());
        }

        /**
         * 不允许beanName重复
         */
        if (this.containsBeanDefinition(beanName)) {
            throw new BeanDefinitionRegistryException(
                    "名字为[" + beanName + "]的bean定义已存在：" + this.getBeanDefinition(beanName));
        }

        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitionMap.get(beanName);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return this.doGetBean(beanName);
    }

    protected Object doGetBean(String beanName) throws Exception {
        Objects.requireNonNull(beanName, "beanName不能为空");
        /**
         * 实现单例
         */
        Object instance = singletonBeanMap.get(beanName);
        if (instance != null) {
            return instance;
        }
        BeanDefinition bd = this.getBeanDefinition(beanName);
        Objects.requireNonNull(bd, "beanName的beanDefinition不存在");
        if (bd.isSingleton()) {
            // 加锁 + 双重检查来保证单例
            // 加锁
            synchronized (this.singletonBeanMap) {
                instance = singletonBeanMap.get(beanName);
                // 加锁后再次检查
                if (instance == null) {
                    instance = doCreateInstance(bd);
                    this.singletonBeanMap.put(beanName, instance);
                }
            }
        } else {
            instance = doCreateInstance(bd);
        }
        return instance;
    }

    private Object doCreateInstance(BeanDefinition bd) throws Exception {
        Class<?> type = bd.getBeanClass();
        Object instance;
        if (type != null) {
            if (StringUtils.isBlank(bd.getFactoryMethodName())) {
                // 构造方法来构建对象
                instance = this.createInstanceByConstructor(bd);
            } else {
                // 静态工厂方法
                instance = this.createInstanceByStaticFactoryMethod(bd);
            }
        } else {
            // 工厂bean方式来构造对象
            instance = this.createInstanceByFactoryBean(bd);
        }

        // 执行初始化方法
        this.doInit(bd, instance);
        return instance;
    }

    /**
     * 通过构造方法来创建对象
     *
     * @param bd
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object createInstanceByConstructor(BeanDefinition bd)
            throws IllegalAccessException, InstantiationException {
        // TODO: 1、获取参数
        // TODO: 2、构造器筛选 isAssignableFrom方法
        try {
            return bd.getBeanClass().newInstance();
        } catch (SecurityException e) {
            System.err.println("创建bean实例异常，beanDefinition:" + bd);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 静态工厂方法
     *
     * @param bd
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object createInstanceByStaticFactoryMethod(BeanDefinition bd)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> type = bd.getBeanClass();
        Method m = type.getMethod(bd.getFactoryMethodName(), null);
        return m.invoke(type, null);
    }

    /**
     * 工厂bean方式来构建对象
     *
     * @param bd
     * @return
     * @throws Exception
     */
    private Object createInstanceByFactoryBean(BeanDefinition bd) throws Exception {
        Object factoryBean = getBean(bd.getFactoryBeanName());
        Method m = factoryBean.getClass().getMethod(bd.getFactoryMethodName(), null);
        return m.invoke(factoryBean, null);
    }

    /**
     * 执行初始化方法
     *
     * @param bd
     * @param instance
     * @throws Exception
     */
    private void doInit(BeanDefinition bd, Object instance) throws Exception {
        // 执行初始化方法
        if (StringUtils.isNotBlank(bd.getInitMethodName())) {
            Method m = instance.getClass().getMethod(bd.getInitMethodName(), null);
            m.invoke(instance, null);
        }
    }

    @Override
    public Class<?> getType(String beanName) throws Exception {
        BeanDefinition bd = this.getBeanDefinition(beanName);
        Class<?> type = bd.getBeanClass();
        if (type != null) {
            if (StringUtils.isBlank(bd.getFactoryMethodName())) {
                // 通过构造方法来实例化bean
                // 无需操作
            } else {
                // 通过静态工厂方法来实例化bean
                type = type.getDeclaredMethod(bd.getFactoryMethodName(), null).getReturnType();
            }
        } else {
            // 通过工厂bean中的成员方法来实例化bean
            // 先要获取工厂bean对象
            type = this.getType(bd.getFactoryBeanName());
            type = type.getDeclaredMethod(bd.getFactoryMethodName(), null).getReturnType();
        }
        return type;
    }

    @Override
    public <T> T getBean(Class<T> beanClass) throws Exception {
        Set<String> beanNames = this.typeMap.get(beanClass);
        Objects.requireNonNull(beanNames, "没有找到该类型的bean:" + beanClass);
        if (beanNames.size() == 1) {
            return (T) getBean(beanNames.iterator().next());
        }
        String primaryBeanName = null;
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            if (beanDefinition != null && beanDefinition.isPrimary()) {
                if (primaryBeanName != null) {
                    throw new Exception(beanClass + "类型的bean存在多个primary的定义");
                } else {
                    primaryBeanName = beanName;
                }
            }
        }
        if (primaryBeanName != null) {
            return (T) getBean(primaryBeanName);
        } else {
            throw new Exception(beanClass + "类型的bean存在多个，但没有设置primary的bean");
        }
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> beanClass) throws Exception {
        Set<String> beanNames = this.typeMap.get(beanClass);
        if (beanNames != null) {
            final Map<String, T> results = new HashMap<>(beanNames.size());
            beanNames.forEach(beanName -> {
                try {
                    results.put(beanName, (T) getBean(beanName));
                } catch (Exception e) {}
            });
            return results;
        }
        return null;
    }

    /**
     * 注冊bean类型的map集合（需要在beandefinition都注册完成后再执行）
     * 
     * @throws Exception
     */
    public void registerTypeMap() throws Exception {
        for (String beanName : this.beanDefinitionMap.keySet()) {
            Class<?> type = this.getType(beanName);
            // 映射本类
            this.registerTypeMap(beanName, type);
            // 还需考虑父类
            this.registerSuperTypeMap(beanName, type);
            // 还需考虑其接口
            this.registerInterfaceTypeMap(beanName, type);
        }
    }

    public void registerTypeMap(String beanName, Class<?> type) throws Exception {
        Set<String> beanNames = this.typeMap.get(type);
        if (beanNames == null) {
            beanNames = new HashSet<>();
            this.typeMap.put(type, beanNames);
        }
        beanNames.add(beanName);
    }

    public void registerSuperTypeMap(String beanName, Class<?> type) throws Exception {
        Class superClass = type.getSuperclass();
        Objects.requireNonNull(superClass, "父类为空");
        if (superClass != null && !superClass.equals(Object.class)) {
            this.registerTypeMap(beanName, superClass);
            this.registerSuperTypeMap(beanName, superClass);
            this.registerInterfaceTypeMap(beanName, superClass);
        }
    }

    public void registerInterfaceTypeMap(String beanName, Class<?> type) throws Exception {
        Class[] interfaces = type.getInterfaces();
        if (interfaces.length > 0) {
            for (Class iface : interfaces) {
                this.registerTypeMap(beanName, iface);
                this.registerInterfaceTypeMap(beanName, iface);
            }
        }
    }



    @Override
    public void close() throws IOException {
        // 执行单例实例的销毁
        for (Map.Entry<String, BeanDefinition> e : this.beanDefinitionMap.entrySet()) {
            String beanName = e.getKey();
            BeanDefinition bd = e.getValue();

            if (bd.isSingleton() && StringUtils.isNotBlank(bd.getDestroyMethodName())) {
                Object instance = this.singletonBeanMap.get(beanName);
                if (instance != null) {
                    try {
                        Method m = instance.getClass().getMethod(bd.getDestroyMethodName(), null);
                        m.invoke(instance, null);
                    } catch (NoSuchMethodException | IllegalAccessException
                            | InvocationTargetException e1) {
                        System.err.println("执行bean[" + beanName + "]的销毁方法异常");
                        e1.printStackTrace();
                    }
                }

            }
        }
    }
}
