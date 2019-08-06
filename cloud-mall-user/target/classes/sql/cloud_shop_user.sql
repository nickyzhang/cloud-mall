CREATE DATABASE IF NOT EXISTS cloud_shop_user;
USE cloud_shop_user;

DROP TABLE cloud_user;
CREATE TABLE IF NOT EXISTS cloud_user  (
  `user_id` BIGINT(20) NOT NULL COMMENT '用户全局唯一标识符',
  `username` VARCHAR(50) COMMENT '用户姓名，可以为空，比如三方登录或者短息登录会创建一个user,但是允许用户名为空',
  `password` VARCHAR(64) NOT NULL COMMENT ' 密码经过哈希之后的值(不能算是加密后的密码)',
  `salt` VARCHAR(64) NOT NULL COMMENT '盐',
  `phone` VARCHAR(50) NOT NULL COMMENT '手机号码',
  `email` VARCHAR(100) COMMENT '邮件地址，允许为空',
  `nickname` VARCHAR(100) COMMENT '邮件地址，允许为空',
  `avator` VARCHAR(512) NOT NULL COMMENT '头像',
  `status` BOOLEAN NOT NULL COMMENT '用户状态，等价于tinyint(1) 0：表示被锁定；1：表示没有被锁定，默认是1 ',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

DROP TABLE cloud_user_auth;
CREATE TABLE IF NOT EXISTS cloud_user_auth  (
  `id` BIGINT(20) NOT NULL COMMENT '用户授权唯一标识符',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户全局唯一标识符，不自动增长',
  `open_id` VARCHAR(50) NOT NULL COMMENT '代表三方登录的时候用户唯一身份的ID',
  `open_type` TINYINT(4) NOT NULL COMMENT '第三方类型,比如QQ:1,微信:2,微博:3,百度:4等',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openid` (`open_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

# DROP TABLE cloud_user_auth;
# CREATE TABLE IF NOT EXISTS cloud_user_auth  (
#   `id` BIGINT(20) NOT NULL COMMENT '用户授权唯一标识符',
#  `user_id` BIGINT(20) NOT NULL COMMENT '用户全局唯一标识符，不自动增长',
# `open_id` VARCHAR(50) NOT NULL COMMENT '代表三方登录的时候用户唯一身份的ID',
# `open_type` TINYINT(4) NOT NULL COMMENT '第三方类型,比如QQ:1,微信:2,微博:3,百度:4等',
#  `access_token` VARCHAR(256) NOT NULL COMMENT 'access_token凭证',
# `expired_time` BIGINT(20) NOT NULL COMMENT '凭证有效时间，单位：秒',
# `refresh_token` VARCHAR(256) NOT NULL COMMENT 'access_token到期后刷新access_token的凭证',
# `create_date` DATETIME NOT NULL COMMENT '创建日期',
# `update_date` DATETIME NOT NULL COMMENT '更新日期',
#  PRIMARY KEY (`id`),
# UNIQUE KEY `openid` (`open_id`)
#) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

DROP TABLE cloud_user_details;
CREATE TABLE IF NOT EXISTS cloud_user_details  (
  `id` BIGINT(20) NOT NULL COMMENT '用户唯一标识符',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户唯一标识符',
  `gender` TINYINT(4) NOT NULL COMMENT '1:男,2:女',
  `age` SMALLINT(6) NOT NULL COMMENT '年龄',
  `birthday` DATE  NOT NULL COMMENT '生日',
  `reg_time` DATETIME  NOT NULL COMMENT '注册时间',
  `reg_ip` DATETIME  NOT NULL COMMENT '注册的IP地址',
  `last_login_time` DATETIME  NOT NULL COMMENT '最近登录时间',
  `last_login_ip` DATETIME  NOT NULL COMMENT '最近登录的IP地址',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

DROP TABLE cloud_user_address;
CREATE TABLE IF NOT EXISTS cloud_user_address (
  `address_id` BIGINT(20) NOT NULL COMMENT '用户地址唯一标识符',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户全局唯一标识符',
  `country` VARCHAR(50) NOT NULL COMMENT '国家',
  `province` VARCHAR(50) NOT NULL COMMENT '省份',
  `city` VARCHAR(50) NOT NULL COMMENT '城市',
  `area` VARCHAR(50) NOT NULL COMMENT '区域',
  `details` VARCHAR(250) NOT NULL COMMENT '详细地址',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`address_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


DROP TABLE cloud_user_coupon;
CREATE TABLE IF NOT EXISTS cloud_user_coupon (
  `coupon_id` BIGINT(20) NOT NULL COMMENT '优惠券ID',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `recieve_time` DATETIME NOT NULL COMMENT '优惠券领取时间',
  `use_time` DATETIME COMMENT '优惠券使用时间',
  `use_state` SMALLINT(6) NOT NULL COMMENT '使用状态: 1 未使用 2 使用中 3已使用',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期'
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;