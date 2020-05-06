CREATE DATABASE IF NOT EXISTS cloud_shop_user;
USE cloud_shop_user;

DROP TABLE cloud_user;
CREATE TABLE IF NOT EXISTS cloud_user  (
  `user_id` BIGINT(20) NOT NULL COMMENT '用户全局唯一标识符',
  `username` VARCHAR(50) DEFAULT NULL COMMENT '用户姓名，可以为空，比如三方登录或者短息登录会创建一个user,但是允许用户名为空',
  `password` VARCHAR(64) DEFAULT NULL COMMENT ' 密码经过哈希之后的值(不能算是加密后的密码)',
  `salt` VARCHAR(64) DEFAULT NULL COMMENT '盐',
  `phone` VARCHAR(50) DEFAULT NULL COMMENT '手机号码',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮件地址，允许为空',
  `nickname` VARCHAR(100) DEFAULT NULL COMMENT '邮件地址，允许为空',
  `avator` VARCHAR(512) DEFAULT '/images/user/default.jpg' COMMENT '头像',
  `status` BOOLEAN NOT NULL DEFAULT 1 COMMENT '用户状态，等价于tinyint(1) 0：表示被锁定；1：表示没有被锁定，默认是1 ',
  `create_date` DATETIME  COMMENT '创建日期',
  `update_date` DATETIME  COMMENT '更新日期',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `name_idx` (`username`) USING BTREE,
  UNIQUE KEY `phone_idx` (`phone`) USING BTREE,
  UNIQUE KEY `email_idx` (`email`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

DROP TABLE cloud_user_auth;
CREATE TABLE IF NOT EXISTS cloud_user_auth  (
  `id` BIGINT(20) NOT NULL COMMENT '用户授权唯一标识符',
  `user_id` BIGINT(20) DEFAULT NULL COMMENT '用户全局唯一标识符，不自动增长',
  `open_id` VARCHAR(50) NOT NULL COMMENT '代表三方登录的时候用户唯一身份的ID',
  `open_type` TINYINT(4) NOT NULL COMMENT '第三方类型,比如QQ:1,微信:2,微博:3,百度:4等',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY `open_id_idx` (`user_id`) USING BTREE,
  UNIQUE KEY `openid` (`open_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

DROP TABLE cloud_user_details;
CREATE TABLE IF NOT EXISTS cloud_user_details  (
  `id` BIGINT(20) NOT NULL COMMENT '用户唯一标识符',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户唯一标识符',
  `gender` VARCHAR(8) DEFAULT NULL COMMENT '性别',
  `age` SMALLINT(6) DEFAULT NULL COMMENT '年龄',
  `birthday` DATE  DEFAULT NULL COMMENT '生日',
  `reg_time` DATETIME  NOT NULL COMMENT '注册时间',
  `reg_ip` VARCHAR(32)  NOT NULL COMMENT '注册的IP地址',
  `last_login_time` DATETIME  NOT NULL COMMENT '最近登录时间',
  `last_login_ip` VARCHAR(32)  NOT NULL COMMENT '最近登录的IP地址',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`user_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

DROP TABLE cloud_user_shipping_address;
CREATE TABLE IF NOT EXISTS cloud_user_shipping_address (
  `address_id` BIGINT(20) NOT NULL COMMENT '用户地址唯一标识符',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户全局唯一标识符',
  `country` VARCHAR(50) NOT NULL COMMENT '国家',
  `province` VARCHAR(50) NOT NULL COMMENT '省份',
  `city` VARCHAR(50) NOT NULL COMMENT '城市',
  `area` VARCHAR(50) NOT NULL COMMENT '区域',
  `details` VARCHAR(250) NOT NULL COMMENT '详细地址',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`address_id`),
  KEY `user_id_idx` (`user_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


DROP TABLE cloud_user_billing_address;
CREATE TABLE IF NOT EXISTS cloud_user_billing_address (
  `address_id` BIGINT(20) NOT NULL COMMENT '用户地址唯一标识符',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户全局唯一标识符',
  `country` VARCHAR(50) NOT NULL COMMENT '国家',
  `province` VARCHAR(50) NOT NULL COMMENT '省份',
  `city` VARCHAR(50) NOT NULL COMMENT '城市',
  `area` VARCHAR(50) NOT NULL COMMENT '区域',
  `details` VARCHAR(250) NOT NULL COMMENT '详细地址',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`address_id`),
  KEY `user_id_idx` (`user_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

DROP TABLE cloud_user_invoice;
CREATE TABLE IF NOT EXISTS cloud_user_invoice_template (
  `template_id` BIGINT(20) NOT NULL COMMENT '发票模板唯一标识符',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户全局唯一标识符',
  `invoice_type` TINYINT(4) NOT NULL DEFAULT '1' COMMENT '发票类型(1: 普通发票 2:增值税专业发票)',
  `company_name` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '单位名称',
  `tax_no` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '纳税人识别号',
  `reg_address` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '注册地址',
  `reg_mobile` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '注册电话',
  `deposit_bank` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '开户银行',
  `bank_no` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '银行账号',
  `receiver_province` VARCHAR(36) NOT NULL DEFAULT '' COMMENT '收票人省份',
  `receiver_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '收票人',
  `receiver_mobile` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '收票人电话',
  `receiver_city` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '收票人城市',
  `receiver_region` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '收票人区域',
  `receiver_address` VARCHAR(256) NOT NULL DEFAULT '' COMMENT '收票人详细地址',
  `invoice_title` VARCHAR(100) NOT NULL COMMENT '发票抬头(可以是个人也可以是公司,默认是公司)',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`template_id`),
  KEY `user_id_idx` (`user_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;