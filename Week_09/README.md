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
