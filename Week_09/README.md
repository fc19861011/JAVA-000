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
insert into rmb_account values (1,0);
insert into usd_account values (1,5);

create table rmb_account_freeze (
  user_id int not null,
  amount int not null,
  PRIMARY KEY (`user_id`) USING BTREE
);

create table usd_account_freeze (
  user_id int not null,
  amount int not null,
  PRIMARY KEY (`user_id`) USING BTREE
);

-- B库 表结构和A库一致
insert into rmb_account values (2,100);
insert into usd_account values (2,0);

```
表结构不太合理，正在调试中
预计周六下午搞定

