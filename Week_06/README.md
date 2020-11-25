# 作业
>  基于电商交易场景（用户、商品、订单），设计一套简单的表结构，提交 DDL 的 SQL 文件到 Github（后面 2 周的作业依然要是用到这个表结构）

### 数据表分析(思维导图)
#### 一、用户表
![用户信息-思维导图](.\img\用户信息-思维导图.png)

#### 二、商品表
![商品信息-思维导图](.\img\商品信息-思维导图.png)

#### 三、订单表   
![订单信息-思维导图](.\img\订单信息-思维导图.png)

### 数据表 DDL   
#### 一、创建数据库
```
CREATE DATABASE `mail_test` CHARACTER SET 'utf8mb4'; 
```
#### 二、买家信息
##### 2.1. 用户信息表

```sql
create table `customers_info` (
    customer_id varchar(32) not null PRIMARY key COMMENT '买家主键',
	username varchar(30) not null COMMENT '用户名',
	nickname varchar(30) COMMENT '昵称',
	truename varchar(30) COMMENT '真实姓名',
	birthday date COMMENT '生日',
	phone_number varchar(20) COMMENT '手机号码',
	email varchar(20) COMMENT '邮箱',
	create_time long not null COMMENT '创建时间'
)
```
##### 2.2. 常用地址信息表

```sql
create table `customers_address` (
    address_id varchar(32) not null COMMENT '地址主键',
	customer_id varchar(32) not null COMMENT '买家主键',
	addr_name varchar(30) not null COMMENT '常用地址名称',
	addressee varchar(30) not null COMMENT '收件人姓名',
	addressee_phone varchar(20) not null  COMMENT '买家主键',
	addressee_phone_bak varchar(20) not null COMMENT '买家主键',
	addr_province varchar(30) not null COMMENT '收件人地址-省份',
	addr_city varchar(30) not null COMMENT '收件人地址-地市',
	addr_county varchar(30) not null COMMENT '收件人地址-县区',
	addr_street varchar(30) not null COMMENT '收件人地址-街道',
	addr_detail varchar(100) not null COMMENT '收件人地址-详细信息',
	is_default char(1) COMMENT '是否默认',
	create_time long not null COMMENT '创建时间',
	PRIMARY KEY (`address_id`) USING BTREE
)
```

##### 2.3. 用户订单信息表

```sql
create table `customers_order` (
	customer_id varchar(32) not null COMMENT '买家主键',
    order_code varchar(32) not null COMMENT '订单编号',
	order_status varchar(30) not null COMMENT '订单状态',
	googs_id varchar(32) not null COMMENT '商品主键',
	goods_title varchar(50) not null COMMENT '商品标题/名称',
	goods_amount varchar(50) not null COMMENT '总价',
	discount_amount varchar(50) not null COMMENT '优惠金额',
	real_payment varchar(50) not null COMMENT '实付款',
	merchant_id varchar(32) not null COMMENT '商家主键',
	merchant_name varchar(50) not null COMMENT '商家名称',
	order_create_time long not null COMMENT '订单创建时间',
	order_complete_time long COMMENT '订单完成时间',
	PRIMARY KEY (`customer_id`, `order_code`) USING BTREE
)
```

#### 三、卖家信息

##### 3.1 卖家基本信息

```sql
create table `merchant_info` (
	merchant_id varchar(32) not null COMMENT '商家主键',
    merchant_name varchar(32) not null COMMENT '商家名称',
	license_info varchar(30) COMMENT '执照信息',
	registration_time long not null COMMENT '注册时间',
	remark varchar(1000) COMMENT '备注',
	PRIMARY KEY (`merchant_id`) USING BTREE
)
```

##### 3.2 卖家经营类型

```sql
create table `merchant_business` (
	merchant_id varchar(32) not null COMMENT '商家主键',
    business_code varchar(32) not null COMMENT '类型编号',
	business_name varchar(30) not null COMMENT '类型名称',
	PRIMARY KEY (`merchant_id`, `business_code`) USING BTREE
)
```

##### 3.3 卖家订单信息

```sql
create table `merchant_order` (
	merchant_id varchar(32) not null COMMENT '商家主键',
    order_code varchar(32) not null COMMENT '订单编号',
	order_status varchar(30) not null COMMENT '订单状态',
	googs_id varchar(32) not null COMMENT '商品主键',
	goods_title varchar(50) not null COMMENT '商品标题/名称',
	goods_amount varchar(50) not null COMMENT '总价',
	discount_amount varchar(50) not null COMMENT '优惠金额',
	real_payment varchar(50) not null COMMENT '实付款',
	customer_id varchar(32) not null COMMENT '买家主键',
	customer_name varchar(50) not null COMMENT '买家名称',
	order_create_time long not null COMMENT '订单创建时间',
	order_complete_time long COMMENT '订单完成时间',
	PRIMARY KEY (`merchant_id`, `order_code`) USING BTREE
)
```

#### 四、商品信息

##### 4.1 商品基本信息

```sql
create table `goods_info` (
	goods_id varchar(32) not null COMMENT '商品主键',
    goods_title varchar(100) not null COMMENT '商品名称',
	goods_sub_title varchar(50) COMMENT '副标题',
	spu_code varchar(30) not null COMMENT 'SPU编号',
	merchant_id varchar(32) not null COMMENT '商家主键',
	merchant_name varchar(50) not null COMMENT '商家名称',
	thumbnail_uri varchar(200) not null COMMENT '缩略图地址',
	delivery_place varchar(100) COMMENT '发货地',
	amount varchar(50) not null COMMENT '展示价格',
	total_stock int(8) not null COMMENT '总库存',
	is_express_free int(1) not null COMMENT '是否包邮',
	express_desc varchar(200) COMMENT '物流描述',
	PRIMARY KEY (`goods_id`) USING BTREE
)
```

##### 4.2  商品介绍信息（图片/短视频）

```sql
create table `goods_media` (
	goods_id varchar(32) not null COMMENT '商品主键',
    media_type char(1) not null COMMENT '类型（图片/短视频）',
	media_uri varchar(50) COMMENT '介绍资料地址',
	PRIMARY KEY (`goods_id`, `media_type`) USING BTREE
)
```

##### 4.3  商品详细信息

```sql
create table `goods_detail` (
	goods_id varchar(32) not null COMMENT '商品主键',
    content  text not null COMMENT '商品介绍（富文本）',
	PRIMARY KEY (`goods_id`) USING BTREE
)
```

##### 4.4  商品SKU信息

```sql
create table `goods_sku` (
	goods_id varchar(32) not null COMMENT '商品主键',
  sku_code varchar(10) not null COMMENT 'sku编号',
	sku_name varchar(50) not null COMMENT 'SKU名称',
	parent_sku_code varchar(10) not null COMMENT '上级SKU编码',
	PRIMARY KEY (`goods_id`, sku_code) USING BTREE
)
```

##### 4.5 商品价格信息

```sql
create table `goods_amount` (
	goods_id varchar(32) not null COMMENT '商品主键',
    sku_code varchar(10) not null COMMENT 'sku编号',
	sku_amount varchar(50) not null COMMENT '当前sku价格',
	PRIMARY KEY (`goods_id`, sku_code) USING BTREE
)
```

##### 4.6 商品库存信息

```sql
create table `goods_stock` (
	goods_id varchar(32) not null COMMENT '商品主键',
    sku_code varchar(10) not null COMMENT 'sku编号',
	sku_stock varchar(50) not null COMMENT '当前sku库存',
	PRIMARY KEY (`goods_id`, sku_code) USING BTREE
)
```

#### 五、订单信息

##### 5.1 订单基本信息

```sql
create table `order_info` (
	order_id varchar(32) not null COMMENT '订单主键',
    order_code varchar(32) not null COMMENT '订单编号',
	order_status char(1) COMMENT '订单状态',
	create_time long not null COMMENT '订单创建时间',
	payment_time long COMMENT '付款时间',
	delivery_time long not null COMMENT '订单发货时间',
	expected_time long not null COMMENT '预计送达时间',
	arrive_time long COMMENT '送达时间',
	complete_time long not null COMMENT '订单结束时间',
	merchant_id varchar(32) not null COMMENT '商家主键',
	merchant_name varchar(50) not null COMMENT '商家名称',
	customer_id varchar(32) not null COMMENT '买家主键',
	customer_name varchar(50) not null COMMENT '买家名称',
	PRIMARY KEY (`order_id`) USING BTREE
)
```

##### 5.2 订单物流信息

```sql
create table `order_logistics_info` (
	order_id varchar(32) not null COMMENT '订单主键',
    order_code varchar(32) not null COMMENT '订单编号',
	logistics_code varchar(30) not null COMMENT '物流单号',
	logistics_status char(1) not null COMMENT '物流状态',
	logistics_company varchar(100) not null COMMENT '物流公司',
	PRIMARY KEY (`order_id`) USING BTREE
)
```

##### 5.3 订单金额信息

```sql
create table `order_amount_info` (
	order_id varchar(32) not null COMMENT '订单主键',
    order_code varchar(32) not null COMMENT '订单编号',
	logistics_amount varchar(50) not null COMMENT '运费',
	discount_amount varchar(50) not null COMMENT '优惠金额',
	order_amount varchar(50) not null COMMENT '总金额',
	real_payment varchar(50) not null COMMENT '实付款',
	payment_method char(1) not null COMMENT '支付方式',
	payment_cardno varchar(20) not null COMMENT '分支卡号',
	PRIMARY KEY (`order_id`) USING BTREE
)
```

##### 5.4 订单商品信息

```sql
create table `order_goods_info` (
	order_id varchar(32) not null COMMENT '订单主键',
    order_code varchar(32) not null COMMENT '订单编号',
	googs_id varchar(32) not null COMMENT '商品主键',
	goods_title varchar(50) not null COMMENT '商品标题/名称',
	goods_amount varchar(50) not null COMMENT '单价',
	goods_count varchar(50) not null COMMENT '数量',
	merchant_id varchar(32) not null COMMENT '商家主键',
	merchant_name varchar(50) not null COMMENT '商家名称',
	googs_snapshot varchar(32) not null COMMENT '商家快照主键（主体信息存在mongo）',
	PRIMARY KEY (`order_id`) USING BTREE
)
```

##### 5.5 订单收件人信息

```sql
create table `order_addressness_info` (
	order_id varchar(32) not null COMMENT '订单主键',
    order_code varchar(32) not null COMMENT '订单编号',
	addressee varchar(30) not null COMMENT '收件人',
	addressee_phone varchar(20) not null COMMENT '收件人电话',
	addressee_phone_bak varchar(20) not null comment '收件人备用电话',
	addr_province varchar(30) not null COMMENT '收件人地址-省份',
	addr_city varchar(30) not null COMMENT '收件人地址-地市',
	addr_county varchar(30) not null COMMENT '收件人地址-县区',
	addr_street varchar(30) not null COMMENT '收件人地址-街道',
	addr_detail varchar(100) not null COMMENT '收件人地址-详细信息',
	is_self_raise char(1) not null COMMENT '是否自提',
	PRIMARY KEY (`order_id`) USING BTREE
)
```



