# 作业一：
设计对前面的订单表数据进行水平分库分表，拆分 2 个库，每个库 16 张表。并在新结构在演示常见的增删改查操作。代码、sql 和配置文件，上传到 Github。

## 一、准备工作

> 模拟场景：
>
> 1、订单表水平拆分为两个库，每个库各16张表
>
> 2、模拟环境中不考虑用户、商家等其他因素，就以订单主键（雪花算法生成）作为分库分表路由的条件
>
> 目的：
>
> 1、实践水平拆分操作
>
> 2、熟悉shardingsphere的使用

### 1、准备两个mysql

### 2、初始化数据库

```sql
CREATE DATABASE `mail_test` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
```

### 3、数据表初始化

数据表初始化有多种方式：

1. 分别在两个mysql中创建16个表
2. 使用proxy进行创建
3. 使用sharding-jdbc配合spring jpa进行初始化

```
-- 建表语句
CREATE TABLE `order_info` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_code` varchar(32) NOT NULL,
  `order_status` tinyint(1) DEFAULT NULL,
  `create_time` bigint(20) NOT NULL,
  `payment_time` bigint(20) DEFAULT NULL,
  `delivery_time` bigint(20) NOT NULL,
  `expected_time` bigint(20) NOT NULL,
  `arrive_time` bigint(20) DEFAULT NULL,
  `complete_time` bigint(20) NOT NULL,
  `merchant_id` bigint(20) NOT NULL,
  `merchant_name` varchar(50) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  PRIMARY KEY (`order_id`) USING BTREE
)
```

## 二、使用shardingsphere-proxy进行水平拆分

### 1、下载shardingsphere-proxy

https://www.apache.org/dyn/closer.cgi/shardingsphere/5.0.0-alpha/apache-shardingsphere-5.0.0-alpha-shardingsphere-proxy-bin.tar.gz

### 2、解压shardingsphere-proxy

![1607437922976](.\img\shardingsphere-proxy.png)

### 3、在根目录下新建ext-lib，并将mysql的驱动包复制到其中。

### 4、配置proxy（注意配置文件中不要出现中文）

- 进入config目录

- 打开server.yaml文件，找到authentication节点，将users节点放开（用于连接proxy的用户名和密码）

  ![1607438158881](.\img\proxy-users.png)

- 配置分片规则（config/config-sharding.yaml）

```yaml
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

######################################################################################################
# 
# Here you can configure the rules for the proxy.
# This example is configuration of sharding rule.
# 
######################################################################################################
#
#schemaName: sharding_db
#
#dataSourceCommon:
#  username: postgres
#  password: postgres
#  connectionTimeoutMilliseconds: 30000
#  idleTimeoutMilliseconds: 60000
#  maxLifetimeMilliseconds: 1800000
#  maxPoolSize: 50
#  minPoolSize: 1
#  maintenanceIntervalMilliseconds: 30000
#
#dataSources:
#  ds_0:
#    url: jdbc:postgresql://127.0.0.1:5432/demo_ds_0?serverTimezone=UTC&useSSL=false
#  ds_1:
#    url: jdbc:postgresql://127.0.0.1:5432/demo_ds_1?serverTimezone=UTC&useSSL=false
#
#rules:
#- !SHARDING
#  tables:
#    t_order:
#      actualDataNodes: ds_${0..1}.t_order_${0..1}
#      tableStrategy:
#        standard:
#          shardingColumn: order_id
#          shardingAlgorithmName: t_order_inline
#      keyGenerateStrategy:
#        column: order_id
#        keyGeneratorName: snowflake
#    t_order_item:
#      actualDataNodes: ds_${0..1}.t_order_item_${0..1}
#      tableStrategy:
#        standard:
#          shardingColumn: order_id
#          shardingAlgorithmName: t_order_item_inline
#      keyGenerateStrategy:
#        column: order_item_id
#        keyGeneratorName: snowflake
#  bindingTables:
#    - t_order,t_order_item
#  defaultDatabaseStrategy:
#    standard:
#      shardingColumn: user_id
#      shardingAlgorithmName: database_inline
#  defaultTableStrategy:
#    none:
#  
#  shardingAlgorithms:
#    database_inline:
#      type: INLINE
#      props:
#        algorithm-expression: ds_${user_id % 2}
#    t_order_inline:
#      type: INLINE
#      props:
#        algorithm-expression: t_order_${order_id % 2}
#    t_order_item_inline:
#      type: INLINE
#      props:
#        algorithm-expression: t_order_item_${order_id % 2}
#  
#  keyGenerators:
#    snowflake:
#      type: SNOWFLAKE
#      props:
#        worker-id: 123

######################################################################################################
#
# If you want to connect to MySQL, you should manually copy MySQL driver to lib directory.
#
######################################################################################################

schemaName: sharding_db

dataSourceCommon:
  username: root
  password: root 
  connectionTimeoutMilliseconds: 30000
  idleTimeoutMilliseconds: 60000
  maxLifetimeMilliseconds: 1800000
  maxPoolSize: 10
  minPoolSize: 1
  maintenanceIntervalMilliseconds: 30000

dataSources:
  ds_0:
    url: jdbc:mysql://127.0.0.1:3306/mail_test?serverTimezone=UTC&useSSL=false
  ds_1:
    url: jdbc:mysql://127.0.0.1:3307/mail_test?serverTimezone=UTC&useSSL=false

rules:
- !SHARDING
  tables:
    order_info:
      actualDataNodes: ds_${0..1}.order_info${0..15}
      tableStrategy:
        standard:
          shardingColumn: order_id
          shardingAlgorithmName: order_info_inline
      keyGenerateStrategy:
        column: order_id
        keyGeneratorName: snowflake
#    t_order_item:
#      actualDataNodes: ds_${0..1}.t_order_item_${0..1}
#      tableStrategy:
#        standard:
#          shardingColumn: order_id
#          shardingAlgorithmName: t_order_item_inline
#      keyGenerateStrategy:
#        column: order_item_id
#        keyGeneratorName: snowflake
#  bindingTables:
#    - t_order,t_order_item
  defaultDatabaseStrategy:
    standard:
      shardingColumn: order_id
      shardingAlgorithmName: database_inline
  defaultTableStrategy:
    none:

  shardingAlgorithms:
    database_inline:
      type: INLINE
      props:
        algorithm-expression: ds_${order_id % 2}
    order_info_inline:
      type: INLINE
      props:
        algorithm-expression: order_info${order_id % 16}
#        allow-range-query-with-inline-sharding: true

#    t_order_item_inline:
#      type: INLINE
#      props:
#        algorithm-expression: t_order_item_${order_id % 2}
  
  keyGenerators:
    snowflake:
      type: SNOWFLAKE
      props:
        worker-id: 123


```

### 5、启动proxy，默认端口3307，可以使用如下命令更改端口号：

```powershell
#在bin目录下运行
.\start.bat 3317
```

### 6、测试

- 测试批量插入100条

  发现数据倾斜很严重，分布在db0.order_info0 以及db1.order_info1

  经查阅[官方文档](https://shardingsphere.apache.org/document/current/cn/user-manual/shardingsphere-jdbc/configuration/built-in-algorithm/keygen/)发现可以给雪花算法增加属性：max-vibration-offset 来解决该问题

  ![1607513906616](.\img\snowflake.png)

  ```yaml
    keyGenerators:
      snowflake:
        type: SNOWFLAKE
        props:
          worker-id: 123
          max-vibration-offset: 15
  ```

  继续测试...

  发现有数据表不可达

  比如：db_0 中 偶数表有数据，奇数表没有数据

  ​            db_1中 奇数表有数据，偶数表没有数据

  库是偶数，单库数据表也是偶数，导致发生了以上的结果。

  如果将数据库分库表达式先除15，然后在对其进行mod 2的操作，使得数据先填充db_0中的所有表，然后再填充db_1中的所有表，不断的重复，数据分布就比较均匀。

  db表达式：

  ```yaml
  database_inline:
        type: INLINE
        props:
         # algorithm-expression: ds_${ (Integer)(order_id / 15) % 2 } 这边要使用整除，否则计算结果可能会出现误差，正确写法请见下面
          algorithm-expression: ds_${order_id.intdiv(15) % 2}
  ```

  测试结果，每个库每个表都有数据，较为均衡：

  - db_0

  ![1607568362848](.\img\db_0.png)

  - db_1

    ![1607568487871](.\img\db_1.png)

### 7、最终配置：

```yaml
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

######################################################################################################
# 
# Here you can configure the rules for the proxy.
# This example is configuration of sharding rule.
# 
######################################################################################################
#
#schemaName: sharding_db
#
#dataSourceCommon:
#  username: postgres
#  password: postgres
#  connectionTimeoutMilliseconds: 30000
#  idleTimeoutMilliseconds: 60000
#  maxLifetimeMilliseconds: 1800000
#  maxPoolSize: 50
#  minPoolSize: 1
#  maintenanceIntervalMilliseconds: 30000
#
#dataSources:
#  ds_0:
#    url: jdbc:postgresql://127.0.0.1:5432/demo_ds_0?serverTimezone=UTC&useSSL=false
#  ds_1:
#    url: jdbc:postgresql://127.0.0.1:5432/demo_ds_1?serverTimezone=UTC&useSSL=false
#
#rules:
#- !SHARDING
#  tables:
#    t_order:
#      actualDataNodes: ds_${0..1}.t_order_${0..1}
#      tableStrategy:
#        standard:
#          shardingColumn: order_id
#          shardingAlgorithmName: t_order_inline
#      keyGenerateStrategy:
#        column: order_id
#        keyGeneratorName: snowflake
#    t_order_item:
#      actualDataNodes: ds_${0..1}.t_order_item_${0..1}
#      tableStrategy:
#        standard:
#          shardingColumn: order_id
#          shardingAlgorithmName: t_order_item_inline
#      keyGenerateStrategy:
#        column: order_item_id
#        keyGeneratorName: snowflake
#  bindingTables:
#    - t_order,t_order_item
#  defaultDatabaseStrategy:
#    standard:
#      shardingColumn: user_id
#      shardingAlgorithmName: database_inline
#  defaultTableStrategy:
#    none:
#  
#  shardingAlgorithms:
#    database_inline:
#      type: INLINE
#      props:
#        algorithm-expression: ds_${user_id % 2}
#    t_order_inline:
#      type: INLINE
#      props:
#        algorithm-expression: t_order_${order_id % 2}
#    t_order_item_inline:
#      type: INLINE
#      props:
#        algorithm-expression: t_order_item_${order_id % 2}
#  
#  keyGenerators:
#    snowflake:
#      type: SNOWFLAKE
#      props:
#        worker-id: 123

######################################################################################################
#
# If you want to connect to MySQL, you should manually copy MySQL driver to lib directory.
#
######################################################################################################

schemaName: sharding_db

dataSourceCommon:
  username: root
  password: root 
  connectionTimeoutMilliseconds: 30000
  idleTimeoutMilliseconds: 60000
  maxLifetimeMilliseconds: 1800000
  maxPoolSize: 10
  minPoolSize: 1
  maintenanceIntervalMilliseconds: 30000

dataSources:
  ds_0:
    url: jdbc:mysql://127.0.0.1:3306/mail_test?serverTimezone=UTC&useSSL=false
  ds_1:
    url: jdbc:mysql://127.0.0.1:3307/mail_test?serverTimezone=UTC&useSSL=false

rules:
- !SHARDING
  tables:
    order_info:
      actualDataNodes: ds_${0..1}.order_info${0..15}
      tableStrategy:
        standard:
          shardingColumn: order_id
          shardingAlgorithmName: order_info_inline
      keyGenerateStrategy:
        column: order_id
        keyGeneratorName: snowflake
#    t_order_item:
#      actualDataNodes: ds_${0..1}.t_order_item_${0..1}
#      tableStrategy:
#        standard:
#          shardingColumn: order_id
#          shardingAlgorithmName: t_order_item_inline
#      keyGenerateStrategy:
#        column: order_item_id
#        keyGeneratorName: snowflake
#  bindingTables:
#    - t_order,t_order_item
  defaultDatabaseStrategy:
    standard:
      shardingColumn: order_id
      shardingAlgorithmName: database_inline
  defaultTableStrategy:
    none:

  shardingAlgorithms:
    database_inline:
      type: INLINE
      props:
        algorithm-expression: ds_${order_id.intdiv(15) % 2}
    order_info_inline:
      type: INLINE
      props:
        algorithm-expression: order_info${order_id % 16}
#        allow-range-query-with-inline-sharding: true

#    t_order_item_inline:
#      type: INLINE
#      props:
#        algorithm-expression: t_order_item_${order_id % 2}
  
  keyGenerators:
    snowflake:
      type: SNOWFLAKE
      props:
        worker-id: 123
        max-vibration-offset: 15

```

## 三、使用shardingsphere-jdbc进行水平拆分

> springboot 版本 2.2.12.RELEASE

### 1、sharding-jdbc：4.0.0-RC2

- 引入jar包

```xml
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
    <version>4.0.0-RC2</version>
</dependency>
```

- 配置

```properties
spring.shardingsphere.datasource.names=ds0,ds1
#ds0 配置
spring.shardingsphere.datasource.ds0.jdbc-url=jdbc:mysql://localhost:3306/mail_test?serverTimezone=UTC&useSSL=false
spring.shardingsphere.datasource.ds0.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.ds0.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds0.username=root
spring.shardingsphere.datasource.ds0.password=root
#ds1 配置
spring.shardingsphere.datasource.ds1.jdbc-url=jdbc:mysql://localhost:3307/mail_test?serverTimezone=UTC&useSSL=false
spring.shardingsphere.datasource.ds1.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.ds1.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.ds1.username=root
spring.shardingsphere.datasource.ds1.password=root
# 分库策略 根据id取模确定数据进哪个数据库
spring.shardingsphere.sharding.default-database-strategy.inline.sharding-column=order_id
spring.shardingsphere.sharding.default-database-strategy.inline.algorithm-expression=ds$->{(order_id.intdiv(15)) % 2}

# 具体分表策略
# 节点 ds0.order_info0,ds0.order_info1... ...
spring.shardingsphere.sharding.tables.order_info.actual-data-nodes=ds$->{0..1}.order_info$->{0..2}
# 分表字段id
spring.shardingsphere.sharding.tables.order_info.table-strategy.inline.sharding-column=order_id


# 分表策略 根据id取模,确定数据最终落在那个表中
spring.shardingsphere.sharding.tables.order_info.table-strategy.inline.algorithm-expression = order_info$->{order_id % 16}
# 使用SNOWFLAKE算法生成主键
spring.shardingsphere.sharding.tables.order_info.key-generator.column = order_id
spring.shardingsphere.sharding.tables.order_info.key-generator.type = SNOWFLAKE
#spring.shardingsphere.sharding.binding-tables=t_order

spring.shardingsphere.props.sql-show=true
```

### 2、sharding-jdbc：5.0.0-alpha

- 引入jar包

```xml
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>shardingsphere-jdbc-core-spring-boot-starter</artifactId>
    <version>5.0.0-alpha</version>
</dependency>
```

- 配置

```properties
spring.shardingsphere.datasource.names=ds0,ds1

spring.shardingsphere.datasource.common.type=com.zaxxer.hikari.HikariDataSource
spring.shardingsphere.datasource.common.driver-class-name=com.mysql.jdbc.Driver
spring.shardingsphere.datasource.common.username=root
spring.shardingsphere.datasource.common.password=root

#ds0 配置
spring.shardingsphere.datasource.ds0.jdbc-url=jdbc:mysql://localhost:3306/mail_test?serverTimezone=UTC&useSSL=false
#ds1 配置
spring.shardingsphere.datasource.ds1.jdbc-url=jdbc:mysql://localhost:3307/mail_test?serverTimezone=UTC&useSSL=false

# 节点 ds0.order_info0,ds0.order_info1... ...
spring.shardingsphere.rules.sharding.tables.order_info.actual-data-nodes = ds$->{0..1}.order_info$->{0..15}

spring.shardingsphere.rules.sharding.binding-tables=order_info

# 使用SNOWFLAKE算法生成主键
spring.shardingsphere.rules.sharding.tables.order_info.key-generate-strategy.column=order_id
spring.shardingsphere.rules.sharding.tables.order_info.key-generate-strategy.key-generator-name=snowflake

# 分库策略 根据id取模确定数据进哪个数据库
spring.shardingsphere.rules.sharding.default-database-strategy.inline.sharding-column=order_id
spring.shardingsphere.rules.sharding.default-database-strategy.inline.sharding-algorithm-name=database-inline
spring.shardingsphere.rules.sharding.sharding-algorithms.database-inline.type=INLINE
spring.shardingsphere.rules.sharding.sharding-algorithms.database-inline.props.algorithm-expression=ds$->{(order_id.intdiv(15)) % 2}
# 具体分表策略
# 分表字段id
spring.shardingsphere.rules.sharding.tables.order_info.table-strategy.inline.sharding-column=order_id
spring.shardingsphere.rules.sharding.tables.order_info.table-strategy.inline.sharding-algorithm-name=order-id-inline
spring.shardingsphere.rules.sharding.sharding-algorithms.order-id-inline.type=INLINE
spring.shardingsphere.rules.sharding.sharding-algorithms.order-id-inline.props.algorithm-expression=order_info$->{order_id % 15}

spring.shardingsphere.rules.sharding.key-generators.snowflake.type=SNOWFLAKE
spring.shardingsphere.rules.sharding.key-generators.snowflake.props.worker-id=123
spring.shardingsphere.rules.sharding.key-generators.snowflake.props.max-vibration-offset=15

# springboot2 + sharding-jdbc5 必须要加如下配置，否则会报"No value bound"
# 可以查看https://github.com/apache/shardingsphere/issues/8306
spring.shardingsphere.rules.replica-query.data-sources.pr_ds.load-balancer-name=robin-1
spring.shardingsphere.rules.replica-query.data-sources.pr_ds.primary-data-source-name=ds0
spring.shardingsphere.rules.replica-query.data-sources.pr_ds.replica-data-source-names=ds0
spring.shardingsphere.rules.replica-query.load-balancers.robin-1.type=ROUND_ROBIN
spring.shardingsphere.rules.replica-query.load-balancers.robin-1.props.workid=123
spring.shardingsphere.rules.replica-query.load-balancers.robin-1.props.sql-show=true

spring.shardingsphere.props.sql-show=true
```

# 作业二
基于 hmily TCC 或 ShardingSphere 的 Atomikos XA 实现一个简单的分布式事务应用 demo（二选一），提交到 Github。
