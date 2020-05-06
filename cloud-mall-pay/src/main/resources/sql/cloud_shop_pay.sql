CREATE DATABASE IF NOT EXISTS cloud_shop_pay;
USE cloud_shop_pay;

DROP TABLE cloud_pay_record;
CREATE TABLE IF NOT EXISTS cloud_pay_record(
  `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '支付记录全局唯一标识符',
  `trade_no` VARCHAR(65) NOT NULL COMMENT '交易流水号',
  `buyer_id` BIGINT(65) UNSIGNED NOT NULL COMMENT '买家ID',
  `order_id` BIGINT(30) UNSIGNED NOT NULL COMMENT '订单主键,全局唯一',
  `order_no` BIGINT(30) UNSIGNED NOT NULL COMMENT '订单业务号,全局唯一',
  `order_status` SMALLINT(6) UNSIGNED NOT NULL COMMENT '订单状态',
  `subject` VARCHAR(65) NOT NULL COMMENT '支付主题',
  `description` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '支付描述',
  `pay_amount` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '支付金额',
  `pay_channel` TINYINT(4) UNSIGNED COMMENT '支付渠道: 1 支付宝，2 微信， 3 银行卡支付，4 paypal支付',
  `pay_ratio` DECIMAL(4,2) UNSIGNED NOT NULL COMMENT '第三方收取的费率',
  `merchant_cost` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '商户成本',
  `merchant_income` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '商户扣除费用后的收入',
  `merchant_account` VARCHAR(65) NOT NULL COMMENT '商户收款账号',
  `pay_time` DATETIME NOT NULL COMMENT '支付完成的时间',
  `is_refund` TINYINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否是退款',
  `refund_amount` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '退款金额',
  `notify_url` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '异步通知地址',
  `notify_time` DATETIME NOT NULL COMMENT '收到第三方支付结果通知时间',
  `return_url` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '页面回调通知地址',
  `deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否删除该记录',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY ('id'),
  INDEX `order_id`(`order_id`(65)) USING BTREE,
  CHECK (`pay_amount` > 0)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


DROP TABLE cloud_pay_refund;
CREATE TABLE IF NOT EXISTS cloud_pay_refund (
  `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '退款记录全局唯一标识符',
  `trade_no` VARCHAR(65) NOT NULL COMMENT '交易流水号',
  `buyer_id` BIGINT(65) UNSIGNED NOT NULL COMMENT '用户ID',
  `order_id` BIGINT(30) UNSIGNED NOT NULL COMMENT '订单主键,全局唯一',
  `order_no` BIGINT(30) UNSIGNED NOT NULL COMMENT '订单业务号,全局唯一',
  `order_status` SMALLINT(6) UNSIGNED NOT NULL COMMENT '订单状态',
  `subject` VARCHAR(65) NOT NULL COMMENT '支付主题',
  `description` VARCHAR(100) NOT NULL DEFAULT '' COMMENT '支付描述',
  `refund_amount` DECIMAL(10,2) UNSIGNED NOT NULL COMMENT '退款金额',
  `refund_channel` TINYINT(4) UNSIGNED NOT NULL COMMENT '退款渠道: 1 支付宝，2 微信， 3 银行卡，4 paypal',
  `refund_status` TINYINT(4) UNSIGNED NOT NULL COMMENT '退款状态: 1 成功 2 失败 3 处理中',
  `notify_url` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '异步通知地址',
  `notify_time` DATETIME NOT NULL COMMENT '收到第三方支付结果通知时间',
  `deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否删除该记录',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY ('id')
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

DROP TABLE cloud_pay_notify;
CREATE TABLE IF NOT EXISTS cloud_pay_notify (
  `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '通知记录全局唯一标识符',
  `order_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单全局唯一标识符',
  `notify_status` TINYINT(4) UNSIGNED NOT NULL COMMENT '通知状态: 1 通知已创建 2 通知成功 3 通知失败 4 请求成功 5 请求失败',
  `notify_type` TINYINT(4) UNSIGNED NOT NULL COMMENT '通知类型: 1 支付通知 2 退款通知',
  `notify_times` TINYINT(4) UNSIGNED NOT NULL DEFAULT 0 COMMENT '当前已通知次数',
  `max_notify_times` TINYINT(4) UNSIGNED NOT NULL DEFAULT 5 COMMENT '最大通知次数',
  `deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否删除该记录',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY ('id'),
  INDEX `order_id`(`order_id`(65)) USING BTREE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


DROP TABLE cloud_pay_notify_log;
CREATE TABLE IF NOT EXISTS cloud_pay_notify_log (
  `id` BIGINT(20) UNSIGNED NOT NULL COMMENT '通知日志记录全局唯一标识符',
  `notify_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '通知记录全局唯一标识符',
  `order_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单全局唯一标识符',
  `request` VARCHAR(2000) NOT NULL COMMENT '请求参数',
  `response` VARCHAR(2000) NOT NULL COMMENT '响应结果',
  `http_status` VARCHAR(20) not null comment 'http状态',
  `deleted` TINYINT(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否删除该记录',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY ('id')
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;