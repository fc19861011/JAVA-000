tuple 元组  可以想象成向量

取元组/向量的局部属性叫投影

投影、连接、选择

交/并/差/笛卡尔乘积

1NF 拆列

2NF 定义主键

3NF BCNF:

SQL解析：

1. 手写，druid mycat(基于druid)
2. antlr4：shardingSphere
3. yacc:mysql,tidb,Cockroachdb

可以自己实现一个SQL解析器

国内好多数据库使用postgresql改的，可以直接改

mysql遵循GPL协议，修改完的产品也必须开源

5.6/5.7的差异：

- 开始支持多主
- 支持MGR高可用(分组复制)
- 分区表
- json
- 性能
- 修复XA（老板的XA有很多低级问题）

5.7/8.0的差异：

- 通用表达式（CTE）（pivot）
- 窗口函数
- 持久化参数
- 自增列持久化
- 默认编码utf8mb4（8.0可以直接使用utf8了）
- DDL原子性
- JSON增强
- 不再对group by进行隐式排序？？==》坑

archive 可以用来做归档，对数据压缩比较厉害

为什么一般单表数据不超过2000万？

bigint 8字节 + 指针长度6字节

页默认大小：16k

一个页可以放下1770（16 *1024 / 14）

mysqld server端用的

另一个是命令行用的

电商中的单位：  聪， 10的18次方个聪才等于1 

一般情况不推荐使用外键、触发器

快速导入：批量写，数据库原生的命令（先去掉索引，约束）



mysql 有个蛋疼的 锁 间隙锁 GAP lock









mysql数据库预热后，检索效率会大大提高

推荐阅读：

- 《mysql技术内幕》
- 《数据库全书》
- 分布式下《数据密集型应用系统设计》

用了text/blob/clob性能下降特别厉害（下降5-10倍的可能）



时间建议存时间戳，不建议村时间

补充知识：

FaaS Serverless  以后的大趋势  headless ====!!!!!趋势

补充作业：

写个sql parser 来解析以下语句：

select id,name from users where id > 100 order by id desc limit 10