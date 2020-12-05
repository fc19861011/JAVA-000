# 作业一：
> 按自己设计的表结构，插入 100 万订单模拟数据，测试不同方式的插入效率
## MySQL5.7安装

### 一、下载地址：

https://downloads.mysql.com/archives/community/

### 二、Windows安装

#### 2.1 准备工作

1. 下载windows版本的MySQL安装包（根据自己的操作系统位数进行选择）

![1606586697642](.\img\mysql_download.png)

2. 将下载文件解压，并拷贝至安装目录

3. 将解压出来的文件夹，复制几份并改名

4. 每个MySQL目录下增加my.ini文件

   ```shell
   [mysqld]
   # 端口号
   port=3306
   server-id=1
   # mysql-5.7.27-winx64的路径
   basedir=C:\Program Files\mysql\mysql-5.7.31-01
   # mysql-5.7.27-winx64的路径+\data
   datadir=C:\Program Files\mysql\mysql-5.7.31-01\data 
   # 最大连接数
   max_connections=200
   # 编码
   character-set-server=utf8mb4
   default-storage-engine=INNODB
   #sql_mode=NO_ENGINE_SUBSTITUTION,STRICT_TRANS_TABLES
   
   # 登录时跳过权限检查
   # skip_grant_tables
   default-time_zone = '+8:00'
   #sql_mode=STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION
   explicit_defaults_for_timestamp=true
   
   [mysql]
   # 编码
   default-character-set=utf8mb4
   ```

#### 2.2 安装

1. 以管理员身份运行命令提示符（或者powershell、Terminal）

2. 在命令提示符中，进入mysql根目录下的bin文件夹

   ```powershell
   cd '.\Program Files\mysql\mysql-5.7.31-01\bin'
   ```

3. 初始化MySQL

   ```powershell
   # 安装多个mysql时，需要增加--defaults-file来指定本次初始化mysql使用的配置文件
   # 如果不需要安装多个mysql，可以省略--defaults-file
   # --console：显示初始化信息，初始化的密码将会显示在其中，方便后面进入数据库
   mysqld --defaults-file='C:\Program Files\mysql\mysql-5.7.31-01\my.ini' --initialize --user=mysql --console
   ```

   ![1606652835331](.\img\setup_01.png)

4. 注册mysql服务

   ```powershell
   # mysqld --install 【服务名】：注册mysql服务，服务名缺省的情况下默认为MYSQL
   # 安装多个mysql时，需要增加--defaults-file来指定本次初始化mysql使用的配置文件
   # 如果不需要安装多个mysql，可以省略--defaults-file
   mysqld --install mysql-master --defaults-file='C:\Program Files\mysql\mysql-5.7.31-01\my.ini'
   ```

   ![1606655357134](.\img\setup_02.png)

   运行结果如上，即服务注册成功

5. 启动mysql

   **命令行启动、用刚注册的服务名来启动**

   ```powershell
   net start mysql-master
   ```

   **手动到windows的服务中启动（services.msc）**

   ![1606655586837](.\img\setup_03.png)

   6. 修改root密码，使用命令行进行修改

      ```powershell
      mysql -P 端口号 -uroot -p
      ```

      按提示输入密码，密码在第3步中产生，输入密码后，进入mysql

   7. 修改root初始密码

      ```powershell
      set password=password('新密码');
      ```

   8. 以上完成后，就可以使用工具连接mysql了

#### 2.3 多个mysql实例安装注意事项：

1. 配置 配置文件my.ini 中的端口号、mysql的根目录与mysql的data目录
2. mysql初始化时需要指定defaults-file，否则第二个mysql初始化和注册服务都会报错
3. 如果配置错误，想要删除服务可以进入注册表进行修改
   1. win+r打开“运行”，输入regedit，打开注册表
   2. 到这个目录：HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services下找到需要删除的服务，右键删除，重启系统后生效。

### 三、Docker 安装

> Docker从1.13版本之后采用时间线的方式作为版本号，分为社区版CE和企业版EE。社区版是免费提供给个人开发者和小型团体使用的，企业版会提供额外的收费服务，比如经过官方测试认证过的基础设施、容器、插件等。社区版按照stable和edge两种方式发布，每个季度更新stable版本，如17.06，17.09；每个月份更新edge版本，如17.09，17.10。
>
> 以下为linux中安装docker ce版本

#### 3.1 安装docker（简单粗暴的方式）

**注意：**Docker 要求 CentOS 系统的内核版本高于 3.10 ，查看本页面的前提条件来验证你的CentOS 版本是否支持 Docker 。

1. 切换cetos yum源

2. 安装docker-ce版本

   ```shell
   yum -y install docker-ce
   # 设置开机启动
   systemctl enable docker
   # 启动docker
   systemctl start docker
   ```

3. 启动docker

   ```shell
   service docker start
   ```

#### 3.2 安装docker-compose

```shell
sudo curl -L https://github.com/docker/compose/releases/download/1.20.1/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose

sudo chmod +x /usr/local/bin/docker-compose
```

#### 3.3 安装mysql

1. docker hub网站中寻找mysql的镜像（https://hub.docker.com/_/mysql?tab=tags&page=1&ordering=last_updated）
2. 在tags中找到自己需要的版本

![image-20201130174635659](.\img\docker-hub.png)

3. 编写compse文件(Description选项卡中有参考版本)，文件名docker-compose.yml

   ```yaml
   # Use root/example as user/password credentials
   version: '3.1'
   services:
     db:
       image: mysql:5.7.29
       ports:
         - "3306:3306"
       environment:                                        
         MYSQL_ROOT_PASSWORD: root
         MYSQL_USER: 'scotter'
         MYSQL_PASS: 'scotter'
       command:
         --default-authentication-plugin=mysql_native_password
         --character-set-server=utf8mb4
         --collation-server=utf8mb4_general_ci
         --explicit_defaults_for_timestamp=true
         --lower_case_table_names=1
       restart: always
       volumes::
         - "/etc/localtime:/etc/localtime"
         - "[宿主主机目录]:/var/lib/mysql"
         - "[宿主主机目录，启动后进行初始化的语句]:/docker-entrypoint-initdb.d"
         - "[宿主主机目录，mysql配置文件目录]:/etc/mysql/conf.d"
   
   ```

4. 将compose文件上传到系统中，然后在该文件所在目录下执行docker compose的命令来安装mysql

   ```shell
   #（如果要看建立过程日志可以不加-d）
   docker-compose up -d
   # 停止容器
   docker-compose stop 
   # 启动容器
   docker-compose start 
   # 删除容器
   docker-compose rm -f 
   ```

5. 如果要安装多个mysql，只需复制compose文件即可，修改映射的端口号以及volumes，多个compose文件不要放在同一个目录

### 四、参考文献

https://blog.csdn.net/weixin_43395911/article/details/99702121

https://blog.csdn.net/weixin_40475396/article/details/82184608

## 批量插入：存储过程(有序插入)

>测试环境：
>
>内存：4G
>
>CPU：Intel(R) Core(TM) i5-4590 CPU @ 3.30GHz
>
>mysql：5.7.29
>
>运行环境：docker swarm
>
>mysql的binlog日志没有开，autocommit关闭状态

### 1.1 单个事务多个Insert into语句的方式

#### 测试情况

| 每次事务提交的insert数量 | 执行时间 | 执行事务次数 |
| ------------------------ | -------- | ------------ |
| 1                        | 100.984s | 1000000      |
| 2                        | 68.951s  | 500000       |
| 5                        | 45.515s  | 200000       |
| 10                       | 38.573s  | 100000       |
| 50                       | 31.555s  | 20000        |
| 100                      | 30.137s  | 10000        |
| 500                      | 28.745s  | 2000         |
| 1000                     | 28.513s  | 1000         |
| 1500                     | 28.441s  | 667          |
| 1800                     | 28.348s  | 556          |
| 2000                     | 28.439s  | 500          |
| 5000                     | 28.296s  | 200          |
| 10000                    | 28.23s   | 100          |
| 100000                   | 28.01s   | 10           |
| 1000000                  | 28.07s   | 1            |

#### 存储过程代码

```sql
CREATE DEFINER=`mail`@`%` PROCEDURE `order_data_init3`( IN `batch_num` INT )
BEGIN
DECLARE
	i INT;
DECLARE
	total_num INT DEFAULT 1000000;

SET i = 0;
START TRANSACTION;
WHILE
	i < total_num DO
INSERT INTO order_info ( order_code, order_status, create_time, payment_time, delivery_time, expected_time, arrive_time, complete_time, merchant_id, merchant_name, customer_id, customer_name )
VALUES
	(
		concat( '234234023423', i ),
		'1',
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		i,
		concat( 'merchant_name', i ),
		i,
		concat( 'customer_name', i ) 
	);
IF
	i % batch_num = 0 THEN
		COMMIT;
	START TRANSACTION;
END IF;
SET i = i + 1;
END WHILE;
COMMIT;
END
```

### 1.2 insert into xx values (),(),()...的方式

#### 测试情况

| insert into 后面的数据量 | 执行时间 | 执行事务次数 |
| ------------------------ | -------- | ------------ |
| 50                       | 27.63s   | 20000        |
| 100                      | 26.181s  | 10000        |
| 500                      | 27.456s  | 2000         |
| 1000                     | 31.954s  | 1000         |
| 1500                     | 36.244s  | 667          |
| 2000                     | 41.674s  | 500          |
| 5000                     | 67.447s  | 200          |
| 10000                    | 109.098s | 100          |
| 1000000                  | 8.601s   | 1            |

#### 存储过程代码

```sql
CREATE DEFINER=`mail`@`%` PROCEDURE `order_data_batch_init2`( IN `batch_num` INT )
BEGIN
DECLARE
	i INT;
DECLARE
	num INT;
DECLARE
	SQL_FOR_INSERT LONGBLOB;

SET num = 0;

SET i = 0;
WHILE
	i < 1000000 DO
IF
i % batch_num = 0 THEN
START TRANSACTION;

SET SQL_FOR_INSERT = concat( 'insert into order_info(order_code, order_status,', 'create_time, payment_time, delivery_time,', 'expected_time, arrive_time, complete_time,', 'merchant_id, merchant_name, customer_id,', 'customer_name) values ' );

SET SQL_FOR_INSERT = concat(
	SQL_FOR_INSERT,
	'(',
	concat( '''', '234234023423', i, '''' ),
	',',
	'''1''',
	',',
	REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
	',',
	REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
	',',
	REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
	',',
	REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
	',',
	REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
	',',
	REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
	',',
	concat( '''', i, '''' ),
	',',
	concat( '''', 'merchant_name', i, '''' ),
	',',
	concat( '''', i, '''' ),
	',',
	concat( '''', 'customer_name', i, '''' ),
	')' 
);
ELSE 
	SET SQL_FOR_INSERT = concat(
		SQL_FOR_INSERT,
		', (',
		concat( '''', '234234023423', i, '''' ),
		',',
		'''1''',
		',',
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		',',
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		',',
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		',',
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		',',
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		',',
		REPLACE ( unix_timestamp( CURRENT_TIMESTAMP ( 3 ) ), '.', '' ),
		',',
		concat( '''', i, '''' ),
		',',
		concat( '''', 'merchant_name', i, '''' ),
		',',
		concat( '''', i, '''' ),
		',',
		concat( '''', 'customer_name', i, '''' ),
		')' 
	);

END IF;
IF
	( ( i + 1 ) % batch_num = 0 ) THEN
		
		SET @SQL = SQL_FOR_INSERT;
	PREPARE stmt 
	FROM
		@SQL;-- 预处理动态sql语句
	EXECUTE stmt;-- 执行sql语句
	DEALLOCATE PREPARE stmt;-- 释放prepare
	COMMIT;
	
	SET num = num + 1;
	
	ELSEIF ( i = 999999 ) THEN
	
	SET @SQL = SQL_FOR_INSERT;
	PREPARE stmt 
	FROM
		@SQL;-- 预处理动态sql语句
	EXECUTE stmt;-- 执行sql语句
	DEALLOCATE PREPARE stmt;-- 释放prepare
	COMMIT;
	
	SET num = num + 1;
	
END IF;

SET i = i + 1;

END WHILE;
SELECT
	num;

END
```

### 1.3 总结

从结果来看：

批量提交多条insert into 语句 要比 insert into values 多个值 效率要高；

- 在理论环境下，在同一个事务中批量提交的越多，性能普遍较好（减少了创建事务的开销，但是会有触发log_buffer刷盘的开销），批量提交的时候需要注意控制事务的大小，事务太大，强制buffer flush的过程中，如果发生日志文件写满了，还没有写完，这样会导致日志不能切换，Mysql就会hang住。（这个时候可以根据文件修改时间来判断日志文件的旋转频率，旋转频率太频繁，说明日志文件较小）。
- insert into values (),()..如果values后面的值过多，会增加语法解析的时间，影响性能（复杂SQL对sql的解析引擎不友好）。
- 存储过程的写法还可以进优化，后续研究下在来优化。

## 批量插入：程序插入
> 框架使用：springboot、hikari
> 网络情况：局域网、100M
### JPA方式

#### 准备工作：

1. 数据连接中加上 logger=Slf4JLogger&profileSQL=true，用来显示 MySQL 执行的 SQL 日志，例如：

   ```yaml
   url: jdbc:mysql://ip:端口/dbname?useUnicode=true&characterEncoding=utf8&useSSL=false&logger=Slf4JLogger&profileSQL=true
   ```

2. 打开 Spring 的事务处理日志，用来观察事务的执行过程

   ```yaml
   logging:
     level:
       org:
         springframework:
           orm:
             jpa: DEBUG
           transaction: trace
         hibernate:
           engine:
             transaction:
               internal:
                 TransactionImpl: debug
           resource:
             jdbc: trace
       com:
         zaxxer:
           hikari: DEBUG
   ```

   ![image-20201203094208870](.\img\jpa-tx-logger.png)

#### 结果

- 单线程情况下，一个事务提交1000000条耗时：3_100_944ms
- 单线程情况下，一个事务提交1000条，共提交1000000条, 耗时:3_968_880ms
- 单线程情况下，一个事务提交1条，共提交1000000条, 耗时:5_538_787ms

#### 总结：

jpa得插入性能奇差无比，每次save时，先要对对象做存在性检查，在进行插入。

从源码上看批量插入也是进行多次的save操作

```java
@Transactional
public <S extends T> List<S> saveAll(Iterable<S> entities) {
    Assert.notNull(entities, "Entities must not be null!");
    List<S> result = new ArrayList();
    Iterator var3 = entities.iterator();

    while(var3.hasNext()) {
        S entity = var3.next();
        result.add(this.save(entity));
    }
    return result;
}
```

### springjdbc方式

#### 准备工作

1. datasource上增加rewriteBatchedStatements=true来开启批量写入

#### 结果

| 每次事务提交的insert数量 | 执行时间 | 执行事务次数 |
| ------------------------ | -------- | ------------ |
| 1000000                  | 41483ms  | 1            |
| 1000                     | 24172ms  | 1000         |
| 5000                     | 21888ms  | 200          |
| 10000                    | 25207ms  | 100          |

### 多线程的方式：

CPU：4核，由于批量插入是IO密集型的操作，线程池大小推荐为8个

每个线程处理125000，总计耗时：20695ms

后面有时间再来继续调试、优化

# 作业二 读写分离 - 动态切换数据源版本 1.0

## 实现思路：
基于springboot的AbstractRoutingDataSource实现动态数据源的切换，结合AOP根据方法名动态注入响应的数据源
## 作业目录:
./homework/mysql-homework/mysql-rw-splitting-v1

# 作业三 读写分离 - 数据库框架版本 2.0
来不及了，就这两天补上