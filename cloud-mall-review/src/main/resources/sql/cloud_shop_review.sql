/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.9 : Database - cloud_shop_review
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cloud_shop_review` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `cloud_shop_review`;

/*Table structure for table `cloud_attr_name` */

DROP TABLE IF EXISTS `cloud_review`;

CREATE TABLE `cloud_review` (
  `review_id` BIGINT(20) NOT NULL COMMENT '评论表主键',
  `sku_id` BIGINT(20) NOT NULL COMMENT '用户id',
  `sku_name` VARCHAR(255) CHARACTER SET utf8 NOT NULL COMMENT 'SKU名字',
  `list_price` NUMERIC(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '市场价',
  `sale_price` NUMERIC(12,2) UNSIGNED NOT NULL DEFAULT '0.00' COMMENT '销售价',
  `sku_desc` VARCHAR(1000) CHARACTER SET utf8 DEFAULT NULL COMMENT 'SKU描述',
  `sku_image_url` VARCHAR(255) DEFAULT NULL COMMENT 'SKU图片路径',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户id',
  `username` VARCHAR(65) CHARACTER SET utf8 NOT NULL COMMENT '用户名称',
  `location` VARCHAR(65) CHARACTER SET utf8 NOT NULL COMMENT '用户所在地方',
  `email` VARCHAR(65) CHARACTER SET utf8 NOT NULL COMMENT '邮件地址',
  `remark_time` DATETIME NOT NULL COMMENT '提交评论时间',
  `score` FLOAT DEFAULT '0' COMMENT '分数，可以计算产品的选购指数',
  `headline` VARCHAR(512) CHARACTER SET utf8 NOT NULL COMMENT '评论标题',
  `remark` VARCHAR(1000) CHARACTER SET utf8 NOT NULL COMMENT '评论',
  `style` TINYINT(4) NOT NULL COMMENT '风格：Casual,Trendy,Sporty,Fashion Forward,Classic,Formal ',
  `quality` TINYINT(4) NOT NULL COMMENT '质量：Low, Average, High',
  `fit` TINYINT(4) NOT NULL COMMENT '匹配程度：Loose, True To Size, Tight ',
  `recommendable` TINYINT(2) NOT NULL COMMENT '是否值得推荐：0 不推荐 1 推荐',
  `deleted` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '标记删除(1), 标记未删除(0)',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`review_id`),
  KEY `sku_id_idx` (`sku_id`),
  KEY `deleted_idx` (`deleted`) USING BTREE
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;