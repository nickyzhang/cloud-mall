CREATE DATABASE IF NOT EXISTS cloud_shop_inventory;
USE cloud_shop_inventory;

DROP TABLE cloud_inventory;
CREATE TABLE IF NOT EXISTS cloud_inventory  (
  `id` BIGINT(20) NOT NULL COMMENT '库存ID',
  `sku_id` BIGINT(20) NOT NULL COMMENT 'Sku ID',
  `stock_name` VARCHAR(50) NOT NULL COMMENT '库存名字',
  `total_stock` INT(11) NOT NULL COMMENT '当前总的库存数量',
  `available_stock` INT(11) NOT NULL COMMENT '当前可销售库存数量',
  `frozen_stock` INT(11) NOT NULL COMMENT '订单占用或者促销占用的库存数量',
  `stock_threshold` INT(11) NOT NULL COMMENT '阀值',
  `deleted` BOOLEAN NOT NULL DEFAULT 0 COMMENT '是否已删除',
  `version` BIGINT(20) NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY `sku_idx` (`sku_id`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

DROP TABLE cloud_inventory_flow;
CREATE TABLE IF NOT EXISTS cloud_inventory_flow(
  `id` BIGINT(20) NOT NULL COMMENT '库存流水ID',
  `sku_id` BIGINT(20) NOT NULL COMMENT 'Sku ID',
  `stock_num_before` INT(11) COMMENT '库存总量修改前的数量',
  `stock_num_after` INT(11) COMMENT '库存总量修改后的数量',
  `stock_num_diff` INT(11) COMMENT '库存总量的改变值',
  `available_num_before` INT(11) COMMENT '可售的库存修改前数量',
  `available_num_after` INT(11) COMMENT '可售的库存修改后数量',
  `available_num_diff` INT(11) COMMENT '可售的库存改变值',
  `order_occupied_num_before` INT(11) COMMENT '订单占用库存修改前的数量',
  `order_occupied_num_after` INT(11) COMMENT '订单占用库存修改后的数量',
  `order_occupied_num_diff` INT(11) COMMENT '订单占用库存改变值',
  `locked_num_before` INT(11) COMMENT '锁定库存修改前的数量',
  `locked_num_after` INT(11) COMMENT '锁定库存修改后的数量',
  `locked_num_diff` INT(11) COMMENT '锁定库存改变值',
  `action_type`  TINYINT(4) NOT NULL COMMENT '修改库存的行为: 1 上传库存 2修改库存总数量 3修改锁定库存 4下单占用库存',
  `stock_from` TINYINT(4) NOT NULL COMMENT '来自于谁的修改行为: 1 库存系统 2 订单中心 3 工厂' ,
  `order_id` INT(11) COMMENT '订单占用会涉及到订单ID',
  `deleted` BOOLEAN NOT NULL DEFAULT 0 COMMENT '是否已删除',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY `sku_idx` (`sku_id`) USING BTREE
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


DROP TABLE cloud_inventory_upload_log;
CREATE TABLE IF NOT EXISTS cloud_inventory_upload_log(
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `upload_type` TINYINT(4) NOT NULL COMMENT '上传类型: 1 Excel 2 CSV 3 API',
  `start_time` DATETIME NOT NULL COMMENT '开始上传时间',
  `end_time` DATETIME NOT NULL COMMENT '上传结束时间',
  `deleted` BOOLEAN NOT NULL DEFAULT 0 COMMENT '是否已删除',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
   PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;


DROP TABLE cloud_inventory_reminder;
CREATE TABLE IF NOT EXISTS cloud_inventory_reminder(
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `sku_id` BIGINT(20) NOT NULL COMMENT 'Sku ID',
  `email` VARCHAR(100)NOT NULL COMMENT '通知的邮件地址',
  `send_status` TINYINT(4) NOT NULL COMMENT '发送状态',
  `deleted` BOOLEAN NOT NULL DEFAULT 0 COMMENT '是否已删除',
  `create_date` DATETIME NOT NULL COMMENT '创建日期',
  `update_date` DATETIME NOT NULL COMMENT '更新日期',
   PRIMARY KEY (`id`)
)ENGINE=INNODB DEFAULT CHARSET=utf8mb4;