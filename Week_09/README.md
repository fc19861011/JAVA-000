# 作业1
- 数据库语句
```sql
create database `rpc-test`;

use `rpc-test`;

CREATE TABLE `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
)

insert into user_info values(null, 'u01');
insert into user_info values(null, 'u02');

```
- 实现功能
- [x] 服务端通过反射或者getBean(Class<?>)方式获取服务对象
- [x] 服务端方案通过方法名、参数类型进行匹配
- [x] 添加异常处理
- [x] 使用aop 或者 字节码实现客户端的代理
- [ ] 尝试使用 Netty+HTTP 作为 client 端传输方式。

```
// 根据提供服务的Class类型实例化对象
UserService userService = RpcClientProxy.create(UserService.class, "http://127.0.0.1:8989/cls");
// 根据反射实例化对象
UserService userService2 = RpcClientProxy.create(UserService.class, "http://127.0.0.1:8989");
```
- aop方式
实现的比较粗暴：   
1. 通过注解中@Import处理类以及定义需扫描的包
2. 处理类筛选出目录下含有@RpcReference注解Field的类
3. 通过实现ApplicationRunner接口，在springboot容器启动完成后动态将@RpcReference注解标注的对象进行实例化

# 作业二
> 结合 dubbo+hmily，实现一个 TCC 外汇交易处理，代码提交到 GitHub:   
> 用户 A 的美元账户和人民币账户都在 A 库，使用 1 美元兑换 7 人民币 ;   
> 用户 B 的美元账户和人民币账户都在 B 库，使用 7 人民币兑换 1 美元 ;   
> 设计账户表，冻结资产表，实现上述两个本地事务的分布式事务。
- 数据库语句
```sql
-- A库
create database foreign_exchange;
use foreign_exchange;
create table rmb_account(
  user_id int not null AUTO_INCREMENT,
  amount int not null,
  PRIMARY KEY (`user_id`) USING BTREE
);

create table usd_account(
  user_id int not null AUTO_INCREMENT,
  amount int not null,
  PRIMARY KEY (`user_id`) USING BTREE
);

CREATE TABLE `freeze_rmb_account` (
  `user_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `freeze_type` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`) USING BTREE
)

CREATE TABLE `freeze_usd_account` (
    `user_id` int(11) NOT NULL,
    `amount` int(11) NOT NULL,
    `freeze_type` bigint(20) NOT NULL,
    PRIMARY KEY (`user_id`) USING BTREE
)

-- B库 表结构和A库一致

-- 数据初始化
update usd_account set amount = 10 where user_id = 1;
update usd_account set amount = 0 where user_id = 2;
update rmb_account set amount = 0 where user_id = 1;
update rmb_account set amount = 50 where user_id = 2;
delete from freeze_rmb_account;
delete from freeze_usd_account;

```
> A库和B库不在同一个数据库中，可以结合shardingsphere proxy进行开发
- 使用proxy情况分析：   
本次案例的数据库设计为两个库，每个库表结构一致，且均都是单表，属于水平的分库，proxy配置的时候没有找到合适的案例，
经过多次尝试，发现这种情况只需配置actualDataNodes以及bindingTables即可实现水平的分库。   
最终配置如下（config-sharding.yaml）：   
```yaml
######################################################################################################
#
# If you want to connect to MySQL, you should manually copy MySQL driver to lib directory.
#
######################################################################################################

schemaName: foreign_exchange
#
dataSourceCommon:
  username: root
  password: root
  connectionTimeoutMilliseconds: 30000
  idleTimeoutMilliseconds: 60000
  maxLifetimeMilliseconds: 1800000
  maxPoolSize: 50
  minPoolSize: 1
  maintenanceIntervalMilliseconds: 30000
#
dataSources:
  ds_0:
    url: jdbc:mysql://localhost:3307/foreign_exchange?serverTimezone=UTC&useSSL=false
  ds_1:
    url: jdbc:mysql://localhost:3306/foreign_exchange?serverTimezone=UTC&useSSL=false
#
rules:
- !SHARDING
  tables:
    rmb_account:
      actualDataNodes: ds_${0..1}.rmb_account
#      tableStrategy:
#        standard:
#          shardingColumn: user_id
#          shardingAlgorithmName: rmb_account_inline
    freeze_rmb_account:
      actualDataNodes: ds_${0..1}.freeze_rmb_account
#      tableStrategy:
#        standard:
#          shardingColumn: user_id
#          shardingAlgorithmName: rmb_account_freeze_inline
    usd_account:
      actualDataNodes: ds_${0..1}.usd_account
#      tableStrategy:
#        standard:
#          shardingColumn: user_id
#          shardingAlgorithmName: usd_account_inline
    freeze_usd_account:
      actualDataNodes: ds_${0..1}.freeze_usd_account
#      tableStrategy:
#        standard:
#          shardingColumn: user_id
#          shardingAlgorithmName: usd_account_freeze_inline 
  bindingTables:
    - rmb_account,freeze_rmb_account,usd_account,freeze_usd_account
  defaultDatabaseStrategy:
    standard:
      shardingColumn: user_id
      shardingAlgorithmName: database_inline
  defaultTableStrategy:
    none:
#  
  shardingAlgorithms:
    database_inline:
      type: INLINE
      props:
        algorithm-expression: ds_${user_id % 2}
#    rmb_account_inline:
#      type: INLINE
#      props:
#        algorithm-expression: rmb_account
#    rmb_account_freeze_inline:
#      type: INLINE
#      props:
#        algorithm-expression: freeze_rmb_account
#    usd_account_inline:
#      type: INLINE
#      props:
#        algorithm-expression: usd_account
#    usd_account_freeze_inline:
#      type: INLINE
#      props:
#        algorithm-expression: freeze_usd_account

```
- 程序设计   
  - 服务划分：
    - 人民币支付模块(人民币支付、人民币收款)
    - 美元支付模块(美元支付、美元收款)
    - 用户模块（兑换美元、兑换人民币）
- 测试过程记录
  - 在美元支付模块中实现美元兑换的流程，并执行   
  如果在hmily的tcc中执行confirm失败后（confirm方法参数不对），程序重启中会自动进行补偿，待有时间研究下实现的原理
  - 在用户模块测试调用兑换美元   
  过程中偶尔会报：not found service provider for : org.dromara.hmily.core.service.HmilyTransactionHandlerFactory   
  本项目（springboot+apache dubbo）只需引入hmily-spring-boot-starter-apache-dubbo以及hmily-annotation，无需引入hmily-dubbo包，多引入包会导致分布式事务失效（折腾了两天）
- 思考（没有做过电商，为了增加思考性，单独将资金冻结表提取出来）
  - 支付和收款的差异处理：   
  **支付时**，先判断余额是否满足扣款，如果满足，则进行扣款，同时将扣除的款项放入冻结表   
  **收款时**，先将收入的款项放入冻结表。实际资金不做操作。   
  **为什么如此**：   
  考虑到支付的并发特性，支付时先进性扣款，可以避免超额支付的情况。   
  收款时先不操作实际资金，也同样是为了防止并发过程中，收取的资金在事务中还未最终被确认成功，就被提前支付的情况。   
  - 资金冻结的问题：   
  如果因余额不足导致无法支付，那么此时资金冻结表也没有数据，在执行cancel的时候就要考虑其特殊性，本案例，是将每次的事务都赋予唯一标识，这样在回滚的过程中就可以避免多个事务间的影响。

