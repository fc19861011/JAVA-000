## MySQL5.7

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