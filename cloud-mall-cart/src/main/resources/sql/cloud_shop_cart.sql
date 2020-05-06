CREATE DATABASE IF NOT EXISTS `cloud_shop_cart` DEFAULT CHARACTER SET utf8mb4;
USE `cloud_shop_cart`;

DROP TABLE IF EXISTS `cloud_cart`;
CREATE TABLE cloud_cart(
  `cart_id` BIGINT(20) NOT NULL COMMENT '购物车id',
  `member_id` BIGINT(20) DEFAULT NULL COMMENT '会员id',
  `sku_id` BIGINT(20) NOT NULL COMMENT '商品id',
  `sku_name` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '商品名字',
  `sku_picture` VARCHAR(255) NOT NULL DEFAULT '' COMMENT '商品图片',
  `unit_price` DECIMAL(10,2) NOT NULL COMMENT '当前单价',
  `before_unit_price` DECIMAL(10,2) NOT NULL COMMENT '价格变动前的单价',
  `price_change` DECIMAL(10,2) NOT NULL COMMENT '降价数量，默认是0',
  `quantity` SMALLINT(6) NOT NULL COMMENT '添加到购物车商品数量',
  `total_price` DECIMAL(10,2) NOT NULL COMMENT '购物车商品总价格',
  `limit_num` INT(3) NOT NULL  DEFAULT '999'  COMMENT '购买数量限制',
  `deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`cart_id`),
  KEY `member_id_idx` (`member_id`) USING BTREE,
  KEY `sku_id_idx` (`sku_id`) USING BTREE,
  KEY `cart_del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `cloud_item_spec`;
CREATE TABLE `cloud_item_spec`(
  `spec_id` BIGINT(20) NOT NULL COMMENT '规格ID',
  `sku_id`  BIGINT(20) NOT NULL COMMENT '商品ID',
  `attr_name` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '属性名字',
  `attr_value` VARCHAR(32) NOT NULL DEFAULT '' COMMENT '属性值',
  `deleted` TINYINT(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`spec_id`),
  KEY `sku_id_idx` (`sku_id`) USING BTREE,
  KEY `spec_del_idx` (`deleted`) USING BTREE
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

