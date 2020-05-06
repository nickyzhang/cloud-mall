/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.26 : Database - cloud_shop_promotion
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cloud_shop_promotion` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `cloud_shop_promotion`;

/*Table structure for table `cloud_activity` */

DROP TABLE IF EXISTS `cloud_activity`;

CREATE TABLE `cloud_activity` (
  `activity_id` bigint(20) NOT NULL COMMENT '促销活动全局唯一标识符',
  `rule_id` bigint(20) NOT NULL COMMENT '促销规则唯一标识符',
  `activity_name` varchar(65) NOT NULL COMMENT '促销活动名称',
  `activity_desc` varchar(255) DEFAULT NULL COMMENT '促销活动描述',
  `image_url` varchar(255) DEFAULT NULL COMMENT '促销活动图片',
  `promotion_link` varchar(255) DEFAULT NULL COMMENT '促销活动连接',
  `activity_status` smallint(6) NOT NULL DEFAULT '1' COMMENT '使用状态: 1 未开始 2 进行中 3已结束',
  `activity_type` smallint(6) NOT NULL DEFAULT '-1' COMMENT '促销活动类型: 1 满减促销 2 单品促销 3 赠品促销 4 满赠促销 5 多买优惠 6 运费促销',
  `promotion_method` smallint(6) NOT NULL DEFAULT '-1' COMMENT '促销方式: 1 直减额 2 直折 3 满额减 4 满额折 5 每满减 6 满件减 7 满件折 8 免运费 9满额免 10 满件免 11 送赠品 12 满额赠 13 满件赠',
  `member_type` smallint(6) NOT NULL DEFAULT '1' COMMENT '促销范围: 1 所有用户 2新用户 3 VIP用户 4 男用户 5 女用户 6 自定义用户',
  `promotion_platform` smallint(6) NOT NULL DEFAULT '1' COMMENT '发行平台: 1 所有 2 主站+App+H5商城 3主站 4 App 5 H5商城 6 手机 7 邮件',
  `promotion_channel` smallint(6) NOT NULL DEFAULT '1' COMMENT '促销渠道: 1 所有 2主站 3App 4 H5商城',
  `quantity_limit` int(11) NOT NULL DEFAULT '0' COMMENT '数量限制(从单个用户角度)',
  `stock_limit` bigint(20) NOT NULL DEFAULT '-1' COMMENT '库存限制(是整个库存参与还是部分库存参与)',
  `activity_tags` varchar(65) DEFAULT NULL COMMENT '促销活动标签',
  `start_time` datetime DEFAULT NULL COMMENT '活动开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '活动结束时间',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`activity_id`),
  KEY `act_name_idx` (`activity_name`) USING BTREE,
  KEY `act_status_idx` (`activity_status`) USING BTREE,
  KEY `act_type_idx` (`activity_type`) USING BTREE,
  KEY `act_del_idx` (`deleted`) USING BTREE,
  KEY `act_start_time_idx` (`start_time`),
  KEY `act_end_time_idx` (`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_activity` */

insert  into `cloud_activity`(`activity_id`,`rule_id`,`activity_name`,`activity_desc`,`image_url`,`promotion_link`,`activity_status`,`activity_type`,`promotion_method`,`member_type`,`promotion_platform`,`promotion_channel`,`quantity_limit`,`stock_limit`,`activity_tags`,`start_time`,`end_time`,`deleted`,`create_time`,`update_time`) values (161377431511367681,161332522259582977,'钜惠大促, 满199减20','钜惠大促, 满199减20','https://pop.nosdn.127.net/fcf5bc94-dc06-4f82-8813-ab5459e4002c?imageView&thumbnail=188x0&quality=85','https://cloud.catalog.com/618.html',1,1,3,1,2,1,100,100,'满减','2019-10-10 00:00:00','2019-10-16 23:59:59',0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(161377432585109505,161332522259588097,'国庆钜惠大促, 每满500减30元','国庆钜惠大促, 每满500减30元','https://haitao.nosdn1.127.net/1c1p8fb6222_400_400.jpg?imageView&thumbnail=240x240&quality=95&type=webp','https://cloud.catalog.com/national-day.html',2,1,4,1,3,2,50,32,'每满减','2019-10-01 00:00:00','2019-10-07 23:59:59',0,'2019-09-28 13:10:01','2019-09-28 13:10:01'),(161377432585110529,161332522259586049,'10月倾情大放送, 满2999享七折','10月倾情大放送, 满2999享七折','https://pop.nosdn.127.net/fcf5bc94-dc06-4f82-8813-ab5459e4002c?imageView&thumbnail=188x0&quality=85','https://cloud.catalog.com/act/october.html',2,2,5,1,7,1,50,22,'满折','2019-10-10 00:00:00','2019-10-15 23:59:59',0,'2019-10-04 15:20:01','2019-10-04 15:20:01'),(161377432585111553,161332522259591169,'男人专场,满2件享95折','男人专场,满2件享95折','https://pop.nosdn.127.net/fcf5bc94-dc06-4f82-8813-ab5459e4002c?imageView&thumbnail=188x0&quality=85','https://cloud.catalog.com/act/mens.html',3,2,7,4,2,1,100,0,'满件折','2019-08-01 00:00:00','2019-08-31 23:59:59',0,'2019-07-31 08:15:42','2019-07-31 08:15:42'),(161377432585112577,161332522259597313,'女生专场, 满500送赠品','女生专场, 满500送赠品','https://pop.nosdn.127.net/fcf5bc94-dc06-4f82-8813-ab5459e4002c?imageView&thumbnail=188x0&quality=85','https://cloud.catalog.com/act/womens.html',1,4,12,5,6,1,80,80,'满额赠','2019-10-10 00:00:00','2019-10-20 23:59:59',0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(161377432585113601,161332522259595265,'免运费','免运费','https://pop.nosdn.127.net/fcf5bc94-dc06-4f82-8813-ab5459e4002c?imageView&thumbnail=188x0&quality=85','https://cloud.catalog.com/act/freeship.html',2,6,8,1,2,1,100,85,'免运费','2019-10-01 00:00:00','2019-10-08 23:59:59',0,'2019-09-28 17:11:21','2019-09-28 17:11:21'),(161377432585114625,161332522259598337,'6.18 钜惠大促, 满2件送赠品','6.18 钜惠大促, 满2件送赠品','https://pop.nosdn.127.net/fcf5bc94-dc06-4f82-8813-ab5459e4002c?imageView&thumbnail=188x0&quality=85','https://cloud.catalog.com/act/freegift.html',3,3,13,1,2,1,50,0,'满件赠','2019-06-15 00:00:00','2019-06-18 23:59:59',0,'2019-06-12 09:45:12','2019-06-12 09:45:12'),(161377432585115649,161332522259594241,'满件免','满件免','https://pop.nosdn.127.net/fcf5bc94-dc06-4f82-8813-ab5459e4002c?imageView&thumbnail=188x0&quality=85','https://cloud.catalog.com/act/freeship.html',2,6,10,1,2,1,100,43,'满件免','2019-10-01 00:00:00','2019-10-20 23:59:59',0,'2019-09-25 09:45:12','2019-09-25 09:45:12');

/*Table structure for table `cloud_activity_rule` */

DROP TABLE IF EXISTS `cloud_activity_rule`;

CREATE TABLE `cloud_activity_rule` (
  `activity_id` bigint(20) NOT NULL COMMENT '促销全局唯一标识符',
  `rule_id` bigint(20) NOT NULL COMMENT '促销全局唯一标识符',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_activity_rule` */

insert  into `cloud_activity_rule`(`activity_id`,`rule_id`,`create_time`,`update_time`) values (152324829083598849,152324829083599873,'2019-06-30 22:32:33','2019-06-30 22:32:33');

/*Table structure for table `cloud_activity_shop` */

DROP TABLE IF EXISTS `cloud_activity_shop`;

CREATE TABLE `cloud_activity_shop` (
  `shop_id` bigint(20) NOT NULL COMMENT '店铺全局唯一标识符',
  `activity_id` bigint(20) NOT NULL COMMENT '促销全局唯一标识符',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_activity_shop` */

/*Table structure for table `cloud_activity_sku` */

DROP TABLE IF EXISTS `cloud_activity_sku`;

CREATE TABLE `cloud_activity_sku` (
  `sku_id` bigint(20) NOT NULL COMMENT 'SKU全局唯一标识符',
  `activity_id` bigint(20) NOT NULL COMMENT '促销全局唯一标识符',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  UNIQUE KEY `act_sku_idx` (`sku_id`,`activity_id`),
  KEY `del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_activity_sku` */

insert  into `cloud_activity_sku`(`sku_id`,`activity_id`,`deleted`,`create_time`,`update_time`) values (118894019127279619,161377432585109505,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(118894019127279619,161377432585111553,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(118894019127279619,161377432585114625,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(118895868110700547,161377432585111553,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(118895868110700547,161377432585114625,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(118896029171974147,161377432585114625,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(118896221371760643,161377432585114625,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(118896283648786435,161377432585110529,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(118896283648786435,161377432585114625,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093937426759683,161377432585111553,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093937426759683,161377432585115649,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093938500501507,161377432585111553,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093938500501507,161377432585115649,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093938500502531,161377432585115649,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093938500503555,161377432585115649,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093938500504579,161377431511367681,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093938500504579,161377432585113601,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093938500505603,161377431511367681,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093938500505603,161377432585113601,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093938500506627,161377431511367681,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120093938500506627,161377432585113601,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907977731,161377432585111553,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907978755,161377432585111553,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907979779,161377432585111553,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907980803,161377432585109505,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907980803,161377432585112577,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907981827,161377432585109505,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907981827,161377432585112577,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907982851,161377431511367681,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907982851,161377432585113601,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907983875,161377431511367681,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120095254907983875,161377432585113601,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120105305131453443,161377432585115649,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(120105305131454467,161377432585115649,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(160824720759981057,161377432585110529,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(160824720759981057,161377432585112577,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(160824720759982081,161377432585110529,0,'2019-10-06 12:27:26','2019-10-06 12:27:26'),(160824720759982081,161377432585112577,0,'2019-10-06 12:27:26','2019-10-06 12:27:26');

/*Table structure for table `cloud_coupon` */

DROP TABLE IF EXISTS `cloud_coupon`;

CREATE TABLE `cloud_coupon` (
  `coupon_id` bigint(20) unsigned NOT NULL COMMENT '优惠券主键',
  `template_id` bigint(20) NOT NULL COMMENT '优惠券模板',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `coupon_no` varchar(20) NOT NULL DEFAULT '' COMMENT '优惠券码',
  `coupon_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '优惠券状态，1 未使用 2 已使用 3 已过期',
  `expired_time` datetime NOT NULL COMMENT '优惠券到期时间',
  `receive_time` datetime DEFAULT NULL COMMENT '接收时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '是否逻辑删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_coupon` */

/*Table structure for table `cloud_coupon_template` */

DROP TABLE IF EXISTS `cloud_coupon_template`;

CREATE TABLE `cloud_coupon_template` (
  `template_id` bigint(20) NOT NULL COMMENT '优惠券模板局唯一标识符',
  `template_no` bigint(20) NOT NULL COMMENT '优惠券模板业务编码',
  `template_status` smallint(6) NOT NULL DEFAULT '1' COMMENT '优惠券模板状态: 1 新建 2 审核中 3 审核不通过 4 已发行 5已下架',
  `rule_id` bigint(20) DEFAULT NULL COMMENT '优惠券计算规则',
  `name` varchar(65) NOT NULL COMMENT '优惠券名称',
  `desc` varchar(255) DEFAULT NULL COMMENT '优惠券描述',
  `image_url` varchar(255) DEFAULT NULL COMMENT '优惠券图片',
  `coupon_type` smallint(6) DEFAULT NULL COMMENT '优惠券计算规则类型: 1 现金券 2 满减券 3 折扣券',
  `composition` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否和其他优惠类型叠加',
  `promotion_method` smallint(6) DEFAULT NULL COMMENT '促销方式: 1 直减额 2 直折 3 满额减 4 满额折 5 每满减 6 满件减 7 满件折 8 免运费 9满额免 10 满件免 11 送赠品 12 满额赠 13 满件赠',
  `member_type` smallint(6) NOT NULL DEFAULT '1' COMMENT '促销范围: 1 所有用户 2新用户 3 会员用户 4 男用户 5 女用户 6 自定义用户',
  `platform_type` smallint(6) NOT NULL DEFAULT '1' COMMENT '平台类型限制: 1 平台券 2 店铺券',
  `use_scope` smallint(6) NOT NULL DEFAULT '1' COMMENT '商品使用范围限制: 1 无限制 2 品牌券 3 品类券 4 单品券',
  `promotion_channel` smallint(6) NOT NULL COMMENT '使用渠道: 1 所有 2 主站 3 App 4 H5商城',
  `launch_method` smallint(6) NOT NULL COMMENT '投放方式: 1可发放 2可领取 ',
  `promotion_platform` smallint(6) NOT NULL DEFAULT '1' COMMENT '促销平台: 1 所有 2 主站+App+H5商城 3主站 4 App 5 H5商城 6 手机 7 邮件',
  `face_value` int(11) NOT NULL COMMENT '优惠券面值',
  `discount` smallint(6) NOT NULL DEFAULT '0' COMMENT '折扣优惠券折扣比例',
  `issue_num` bigint(20) NOT NULL COMMENT '发行数量',
  `receive_num` bigint(20) NOT NULL COMMENT '已经领取的优惠券数量',
  `receive_num_limit` int(11) NOT NULL DEFAULT '1' COMMENT '每一个用户限领数量',
  `used_num` bigint(20) NOT NULL COMMENT '已经使用的优惠券数量',
  `used_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '已经使用的优惠券金额',
  `amount_limit` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '发放优惠券金额限制，不能超过这个额度',
  `issue_time` datetime DEFAULT NULL COMMENT '优惠券发行时间',
  `expire_time` datetime DEFAULT NULL COMMENT '优惠券到期时间',
  `valid_days` int(11) NOT NULL COMMENT '优惠券有效天数',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`template_id`),
  KEY `template_name_idx` (`name`) USING BTREE,
  KEY `coupon_type_idx` (`coupon_type`) USING BTREE,
  KEY `promotion_method_idx` (`promotion_method`) USING BTREE,
  KEY `use_scope_idx` (`use_scope`) USING BTREE,
  KEY `del_idx` (`deleted`) USING BTREE,
  KEY `issue_time` (`issue_time`) USING BTREE,
  KEY `expire_time` (`expire_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_coupon_template` */

insert  into `cloud_coupon_template`(`template_id`,`template_no`,`template_status`,`rule_id`,`name`,`desc`,`image_url`,`coupon_type`,`composition`,`promotion_method`,`member_type`,`platform_type`,`use_scope`,`promotion_channel`,`launch_method`,`promotion_platform`,`face_value`,`discount`,`issue_num`,`receive_num`,`receive_num_limit`,`used_num`,`used_amount`,`amount_limit`,`issue_time`,`expire_time`,`valid_days`,`deleted`,`create_time`,`update_time`) values (161395996507504641,161395996507505665,1,161332522259578881,'无门槛立减100元','无门槛立减100元','http://cloud.static.com/image/coupon/01.jpg',2,0,1,2,1,4,1,2,2,100,0,1000,1,1,0,'0.00','100.00','2019-10-08 00:00:01','2019-11-08 23:59:59',30,0,'2019-10-06 17:15:36','2019-10-06 17:15:36'),(161395997581246465,161395997581247489,5,161332522259579905,'无门槛打九折','无门槛打九折','http://cloud.static.com/image/coupon/02.jpg',3,0,2,5,1,3,1,1,2,0,90,1000,0,1,0,'0.00','120.00','2019-09-15 00:00:01','2019-10-15 23:59:59',30,0,'2019-09-10 09:15:25','2019-09-10 09:15:25'),(161395997581248513,161395997581249537,1,161332522259581953,'满99减15','满99减15','http://cloud.static.com/image/coupon/03.jpg',2,0,3,1,1,2,1,2,2,15,0,10000,0,1,0,'0.00','15.00','2019-10-01 00:00:01','2019-10-31 23:59:59',30,0,'2019-09-25 09:15:25','2019-09-25 09:15:25'),(161395997581250561,161395997581251585,1,161332522259582977,'满199减20','满199减20','http://cloud.static.com/image/coupon/04.jpg',2,0,3,4,1,4,1,2,2,20,0,1000,0,1,0,'0.00','20.00','2019-10-07 00:00:01','2019-10-31 23:59:59',30,0,'2019-10-06 17:15:36','2019-10-06 17:15:36'),(161395997581252609,161395997581253633,1,161332522259580929,'满68减10','满68减10','http://cloud.static.com/image/coupon/05.jpg',2,0,3,1,1,1,1,2,2,10,0,5000,0,1,0,'0.00','10.00','2019-06-15 00:00:01','2019-06-18 23:59:59',5,0,'2019-06-12 10:11:12','2019-06-12 10:11:12'),(162536577686306817,162536577686307841,2,161332522259578881,'直减100','直减100','',1,0,1,2,1,4,1,2,3,100,0,1000,0,1,0,'0.00','100.00',NULL,'2019-12-31 23:59:59',30,0,'2019-10-19 00:19:45','2019-10-19 10:37:57');

/*Table structure for table `cloud_coupon_template_brand` */

DROP TABLE IF EXISTS `cloud_coupon_template_brand`;

CREATE TABLE `cloud_coupon_template_brand` (
  `brand_id` bigint(20) NOT NULL COMMENT '品牌全局唯一标识符',
  `template_id` bigint(20) NOT NULL COMMENT '优惠券模板全局唯一标识符',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  UNIQUE KEY `coupon_brand_idx` (`brand_id`,`template_id`),
  KEY `del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_coupon_template_brand` */

/*Table structure for table `cloud_coupon_template_cat` */

DROP TABLE IF EXISTS `cloud_coupon_template_cat`;

CREATE TABLE `cloud_coupon_template_cat` (
  `cat_id` bigint(20) NOT NULL COMMENT '品类全局唯一标识符',
  `template_id` bigint(20) NOT NULL COMMENT '优惠券模板全局唯一标识符',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  UNIQUE KEY `coupon_cat_idx` (`cat_id`,`template_id`),
  KEY `del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_coupon_template_cat` */

insert  into `cloud_coupon_template_cat`(`cat_id`,`template_id`,`deleted`,`create_time`,`update_time`) values (117503439713337347,162536577686306817,0,'2019-10-19 00:19:45','2019-10-19 00:19:45');

/*Table structure for table `cloud_coupon_template_shop` */

DROP TABLE IF EXISTS `cloud_coupon_template_shop`;

CREATE TABLE `cloud_coupon_template_shop` (
  `shop_id` bigint(20) NOT NULL COMMENT '店铺全局唯一标识符',
  `template_id` bigint(20) NOT NULL COMMENT '优惠券模板全局唯一标识符',
  `deleted` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  UNIQUE KEY `coupon_shop_idx` (`shop_id`,`template_id`),
  KEY `del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_coupon_template_shop` */

/*Table structure for table `cloud_coupon_template_sku` */

DROP TABLE IF EXISTS `cloud_coupon_template_sku`;

CREATE TABLE `cloud_coupon_template_sku` (
  `sku_id` bigint(20) NOT NULL COMMENT 'SKU全局唯一标识符',
  `template_id` bigint(20) NOT NULL COMMENT '优惠券模板全局唯一标识符',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  UNIQUE KEY `coupon_sku_idx` (`sku_id`,`template_id`),
  KEY `del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_coupon_template_sku` */

insert  into `cloud_coupon_template_sku`(`sku_id`,`template_id`,`deleted`,`create_time`,`update_time`) values (118894019127279619,161395996507504641,0,'2019-10-31 16:51:34','2019-11-01 15:51:51'),(118894019127279619,161395997581250561,0,'2019-11-01 16:54:35','2019-11-01 16:54:43');

/*Table structure for table `cloud_coupon_use_record` */

DROP TABLE IF EXISTS `cloud_coupon_use_record`;

CREATE TABLE `cloud_coupon_use_record` (
  `id` bigint(20) NOT NULL COMMENT '使用记录ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `template_id` bigint(20) NOT NULL COMMENT '优惠券模板ID',
  `coupon_id` bigint(20) NOT NULL COMMENT '优惠券ID',
  `coupon_no` varchar(20) DEFAULT NULL COMMENT '优惠券业务编码',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `order_no` bigint(20) NOT NULL COMMENT '订单业务码',
  `used_amount` decimal(12,2) DEFAULT '0.00' COMMENT '抵扣金额',
  `use_time` datetime NOT NULL COMMENT '使用时间',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`),
  KEY `coupon_id_idx` (`coupon_id`) USING BTREE,
  KEY `user_id_idx` (`user_id`) USING BTREE,
  KEY `order_id_idx` (`order_id`) USING BTREE,
  KEY `del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_coupon_use_record` */

/*Table structure for table `cloud_promotion_free_gift` */

DROP TABLE IF EXISTS `cloud_promotion_free_gift`;

CREATE TABLE `cloud_promotion_free_gift` (
  `rule_id` bigint(20) NOT NULL COMMENT '促销规则',
  `sku_id` bigint(20) NOT NULL COMMENT '赠品',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  UNIQUE KEY `rule_gift_idx` (`sku_id`,`rule_id`),
  KEY `del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_promotion_free_gift` */

insert  into `cloud_promotion_free_gift`(`rule_id`,`sku_id`,`deleted`,`create_time`,`update_time`) values (161332522259596289,118896283648786435,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259597313,120095254907983875,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259598337,120095254907983875,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259598337,120105305131453443,0,'2019-10-06 00:50:21','2019-10-06 00:50:21');

/*Table structure for table `cloud_promotion_rule` */

DROP TABLE IF EXISTS `cloud_promotion_rule`;

CREATE TABLE `cloud_promotion_rule` (
  `rule_id` bigint(20) NOT NULL COMMENT '促销规则全局唯一标识符',
  `rule_name` varchar(64) NOT NULL COMMENT '促销规则名称',
  `rule_type` smallint(6) NOT NULL COMMENT '规则类型: 1 直减额 2 直折 3 满额减 4 满额折 5 每满减 6 满件减 7 满件折 8 免运费 9满额免 10 满件免 11 送赠品 12 满额赠 13 满件赠',
  `ladder_spend_money` int(11) DEFAULT NULL COMMENT '阶梯减免的门槛额度',
  `per_spend_money` int(11) DEFAULT NULL COMMENT '每减免的门槛额度',
  `ladder_quantity` int(11) DEFAULT NULL COMMENT '阶梯减免的门槛数量',
  `money_off` int(11) DEFAULT NULL COMMENT '减免的金额',
  `discount_off` int(11) DEFAULT NULL COMMENT '减免的折扣',
  `num_off` int(6) DEFAULT NULL COMMENT '减免的数量',
  `free_ship` tinyint(4) DEFAULT '0' COMMENT '是否免运费',
  `freight_off` int(11) DEFAULT NULL COMMENT '减免的运费',
  `free_gift` tinyint(4) DEFAULT '0' COMMENT '是否赠送礼品',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` datetime NOT NULL COMMENT '更新日期',
  PRIMARY KEY (`rule_id`),
  KEY `rule_name_idx` (`rule_name`) USING BTREE,
  KEY `rule_type_idx` (`rule_type`) USING BTREE,
  KEY `del_idx` (`deleted`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `cloud_promotion_rule` */

insert  into `cloud_promotion_rule`(`rule_id`,`rule_name`,`rule_type`,`ladder_spend_money`,`per_spend_money`,`ladder_quantity`,`money_off`,`discount_off`,`num_off`,`free_ship`,`freight_off`,`free_gift`,`deleted`,`create_time`,`update_time`) values (161332522259578881,'无门槛减100',1,NULL,NULL,NULL,100,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259579905,'无门槛打九折',2,NULL,NULL,NULL,NULL,90,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259580929,'满68减10',3,68,NULL,NULL,10,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259581953,'满99减15',3,99,NULL,NULL,15,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259582977,'满199减20',3,199,NULL,NULL,20,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259584001,'满499减40',3,499,NULL,NULL,40,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259585025,'满999享受八折',5,999,NULL,NULL,80,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259586049,'满2999享七折',5,2999,NULL,NULL,70,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259587073,'每满200减10元',4,NULL,200,NULL,10,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259588097,'每满500减30元',4,NULL,500,NULL,30,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259589121,'满2件减30',6,NULL,NULL,2,30,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259590145,'满2件减25',6,NULL,NULL,2,25,NULL,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259591169,'满2件享95折',6,NULL,NULL,2,NULL,95,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259592193,'满3件8折',6,NULL,NULL,3,NULL,80,NULL,NULL,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259593217,'满300免运费',9,300,NULL,NULL,NULL,NULL,NULL,1,-1,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259594241,'满2件免运费',10,NULL,NULL,2,NULL,NULL,NULL,1,6,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259595265,'免运费',8,NULL,NULL,NULL,NULL,NULL,NULL,1,-1,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259596289,'送赠品',11,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259597313,'满500送赠品',12,500,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,0,'2019-10-06 00:50:21','2019-10-06 00:50:21'),(161332522259598337,'满2件送赠品',13,NULL,NULL,2,NULL,NULL,NULL,1,NULL,0,0,'2019-10-06 00:50:21','2019-10-06 00:50:21');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
