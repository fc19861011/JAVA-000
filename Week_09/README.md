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
- [ ] 使用aop 或者 字节码实现客户端的代理
- [ ] 尝试使用 Netty+HTTP 作为 client 端传输方式。

# 作业二
