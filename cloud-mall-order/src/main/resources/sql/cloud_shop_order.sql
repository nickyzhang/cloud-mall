CREATE DATABASE IF NOT EXISTS `cloud_shop_order` DEFAULT CHARACTER SET utf8mb4;
USE `cloud_shop_order`;

DROP TABLE IF EXISTS `cloud_order`;
CREATE TABLE cloud_order(
  `order_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单id',
  `order_no` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单号',
  `user_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '用户id',
  `trade_no` BIGINT(20) UNSIGNED COMMENT '交易号',
  `parent_id` BIGINT (20),
  `order_type` TINYINT(4) UNSIGNED NOT NULL DEFAULT '1' COMMENT '订单类型(1 普通订单,2 秒杀订单)',
  `order_status` SMALLINT(6) UNSIGNED NOT NULL DEFAULT '1' COMMENT '订单状态(1 待付款,2 待发货,3 待收货, 4 已完成 5 已取消 6 售后中 7 已关闭 8 待评价)',
  `shipping_method` TINYINT(4) UNSIGNED NOT NULL DEFAULT '1' COMMENT '配送方式(1 标准 2 快递 )',
  `order_channel` TINYINT(4) UNSIGNED NOT NULL DEFAULT '1' COMMENT '订单来源渠道(1 PC 2 App 3 H5)',
  `total_amount` NUMERIC(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
  `freight` NUMERIC(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '运费金额',
  `activity_amount` NUMERIC(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '待支付金额',
  `coupon_amount` NUMERIC(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '优惠总金额',
  `payment_amount` NUMERIC(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '应支付金额',
  `payment_time` datetime COMMENT '支付时间',
  `delivery_time` datetime COMMENT '发货时间',
  `receive_time` datetime COMMENT '收货时间',
  `deleted` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`order_id`),
  KEY `order_no_idx` (`order_no`) USING BTREE,
  KEY `user_id_idx` (`user_id`) USING BTREE,
  KEY `order_del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `cloud_order_item`;
CREATE TABLE cloud_order_item(
  `item_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单明细id',
  `order_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单id',
  `order_no` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单号',
  `cat_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '类目id',
  `brand_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '品牌id',
  `product_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '产品id',
  `sku_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'SKU id',
  `sku_code` VARCHAR(30) NOT NULL DEFAULT '' COMMENT 'SKU 编码',
  `sku_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'SKU 名字',
  `image_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'SKU 图片路径',
  `unit_price` NUMERIC(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '单价',
  `quantity` SMALLINT(6) NOT NULL DEFAULT '1' COMMENT '购买的商品数量',
  `coupon_amount` NUMERIC(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '占用的优惠券金额',
  `activity_amount` NUMERIC(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '占用的活动优惠',
  `sub_total_amount` DECIMAL (21),
  `deleted` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` DATETIME COMMENT '创建日期',
  `update_time` DATETIME COMMENT '更新日期',
  PRIMARY KEY (`item_id`),
  KEY `order_no_idx` (`order_no`) USING BTREE,
  KEY `order_id_idx` (`order_id`) USING BTREE,
  KEY `order_item_del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `cloud_order_operate_log`;
CREATE TABLE cloud_order_operate_log(
  `log_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单操作日志id',
  `order_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单id',
  `order_no` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单号',
  `type` TINYINT(4) UNSIGNED NOT NULL COMMENT '日志类型(1:插入 2:修改)',
  `order_status` SMALLINT(6) UNSIGNED NOT NULL DEFAULT '1' COMMENT '订单状态(1 待付款,2 待发货,3 待收货, 4 已完成 5 已取消 6 售后中 7 已关闭 8 待评价)',
  `desc` VARCHAR(1000) NOT NULL DEFAULT '' COMMENT '操作描述',
  `operator` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '操作人',
  `operateTime` DATETIME COMMENT '操作日期',
  `deleted` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` DATETIME COMMENT '创建日期',
  `update_time` DATETIME COMMENT '更新日期',
  PRIMARY KEY (`log_id`),
  KEY `order_no_idx` (`order_no`) USING BTREE,
  KEY `order_id_idx` (`order_id`) USING BTREE,
  KEY `order_log_del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `cloud_order_after_sales`;
CREATE TABLE cloud_order_after_sales(
  `service_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '售后服务id',
  `order_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单id',
  `order_no` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单号',
  `after_status` TINYINT(4) UNSIGNED NOT NULL COMMENT '售后状态(1 待审核 2 待上门取件 3 待邮寄 4 处理中 5 退款中 6 已完成 7 待评价)',
  `apply_type` TINYINT(4) UNSIGNED NOT NULL COMMENT '售后类型: 1 退货 2 换货',
  `sku_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'SKU 名字',
  `image_url` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'SKU 图片路径',
  `submit_count` SMALLINT(6) NOT NULL DEFAULT '1' COMMENT '提交数量',
  `return_method` TINYINT(4) UNSIGNED NOT NULL COMMENT '返回方式: 1 上门取件 2自寄',
  `take_address` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '取件地址',
  `take_time` DATETIME COMMENT '取件时间',
  `receiver_address` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '收件人地址',
  `handling_way` TINYINT(4) NOT NULL COMMENT '1 PUPAWAY:退货入库;2 REDELIVERY:重新发货;3 RECLAIM-REDELIVERY:不要求归还并重新发货; 4 REFUND:退款; 5 COMPENSATION:不退货并赔偿',
  `customer_name` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '顾客名字',
  `mobile` VARCHAR(20) NOT NULL DEFAULT '' COMMENT '顾客电话',
  `reason` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '申请售后原因',
  `deleted` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` DATETIME COMMENT '创建日期',
  `update_time` DATETIME COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY `order_no_idx` (`order_no`) USING BTREE,
  KEY `order_id_idx` (`order_id`) USING BTREE,
  KEY `order_after_sales_del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `cloud_order_refund`;
CREATE TABLE cloud_order_refund(
  `refund_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '退款id',
  `service_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '售后服务id',
  `order_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单id',
  `order_no` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单号',
  `refund_amount` NUMERIC(12,2) NOT NULL COMMENT '退款金额',
  `refund_status` TINYINT(4) UNSIGNED NOT NULL DEFAULT '1' COMMENT '退款状态(1 退款中,2 已退款)',
  `arrive_time` DATETIME COMMENT '到账时间',
  `deleted` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` DATETIME COMMENT '创建日期',
  `update_time` DATETIME COMMENT '更新日期',
  PRIMARY KEY (`refund_id`),
  KEY `order_no_idx` (`order_no`) USING BTREE,
  KEY `order_id_idx` (`order_id`) USING BTREE,
  KEY `service_id_idx` (`service_id`) USING BTREE,
  KEY `order_refund_del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `cloud_order_remark`;
CREATE TABLE cloud_order_remark(
  `remark_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单评论id',
  `order_id` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单id',
  `order_no` BIGINT(20) UNSIGNED NOT NULL COMMENT '订单号',
  `package_score` TINYINT(4) DEFAULT '3' COMMENT '物流包装打分 1: 很差 2: 一般 3:满意 4:非常满意 5:无可挑剔',
  `delivery_score` TINYINT(4) DEFAULT '3' COMMENT '发货速度打分 1: 很差 2: 一般 3:满意 4:非常满意 5:无可挑剔',
  `attitude_score` TINYINT(4) DEFAULT '3' COMMENT '物流人员态度打分 1: 很差 2: 一般 3:满意 4:非常满意 5:无可挑剔',
  `goodsScore` TINYINT(4) DEFAULT '3' COMMENT '商品打分 1: 很差 2: 一般 3:满意 4:非常满意 5:无可挑剔',
  `remark` VARCHAR(1000) COMMENT '评论',
  `pictures` VARCHAR(1000) COMMENT '上传图片列表',
  `remark_time` DATETIME COMMENT '评论时间',
  `deleted` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` DATETIME COMMENT '创建日期',
  `update_time` DATETIME COMMENT '更新日期',
  PRIMARY KEY (`remark_id`),
  KEY `order_no_idx` (`order_no`) USING BTREE,
  KEY `order_id_idx` (`order_id`) USING BTREE,
  KEY `order_remark_del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;