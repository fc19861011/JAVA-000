## 作业
### 第一题：
> 写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）。
- 设计原则   
-- 抽象，行为的分类抽象（接口）   
-- 通过继承来扩展功能
-- 面向接口编程   
-- 单一职责   

- bean定义信息接口（BeanDefinition）
**包含**   
1、bean实例化方式（构造器、factoryBean生产）   
2、是否为单例   
3、bean初始化和销毁的方法   
4、bean定义信息的校验   

- bean工厂接口（BeanFactory）   
**应具备的核心功能**   
-- 根据beanName获取bean实例  
**应具备的行为**   
-- 具有接收bean定义信息的行为

- beanDefinition的注册接口(BeanDefinitionRegistry)    
具备的功能：   
-- 注册beanDefinition   
-- 根据beanName获取beanDefinition   
-- 判断beanName的beanDefinition是否存在   

- bean工厂的实现（DefaultBeanFactory）   
-- 实现bean工厂接口BeanFactory以及beandefinition的注册接口（BeanDefinitionRegistry）   
-- 实现bean的实例化（单例、多例）   
-- 实现getBean(beanName)方法
 
- 提前实例化单例Bean的beanFactory(PreBuildBeanFactory)
-- 继承DefaultBeanFactory   
-- 实现提前注册的行为   
  


### 第二题
给前面课程提供的 Student/Klass/School 实现自动配置和 Starter

### 第三题
研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
- 1）使用 JDBC 原生接口，实现数据库的增删改查操作。
- 2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
- 3）配置 Hikari 连接池，改进上述操作。