## IOC容器基本信息

IOC是什么（Inversion of Control）：控制反转，也称依赖倒置

> bean：组件，类的对象
>
> 对象由自己创建，转化为从IOC中取，这样可以做到面向接口编程，使用者与具体类解耦，易扩展、替换实现者。使得代码更为简洁。也为AOP编程提供了基础。

IOC容器负责闯闯、管理实例，向使用者提供实例，属于工厂模式的实例。

## Bean工厂

bean工厂具备的基本行为：

1. 通过工厂模式，输出bean(Object getBean(String beanName))
2. 具备接收bean定义信息的行为

bean定义注册接口中应定义哪些方法：注册、获取bean定义

注册的bean定义信息如何区分：每个bean定义有一个唯一的名称

创建bean：

1. new 构造方法
2. 静态工厂方法
3. 成员工厂方法

beanFactory 和 factoryBean 区别：

beanFactory用来生产所有的Bean对象，包含factoryBean



