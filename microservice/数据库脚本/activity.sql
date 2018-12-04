/*
Navicat MySQL Data Transfer

Source Server         : 192.168.11.156
Source Server Version : 50719
Source Host           : 192.168.11.156:3306
Source Database       : activity

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-07-19 14:13:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity_award
-- ----------------------------
DROP TABLE IF EXISTS `activity_award`;
CREATE TABLE `activity_award` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `award_name` varchar(50) DEFAULT NULL COMMENT '奖品名称',
  `award_info` varchar(255) DEFAULT NULL COMMENT '奖品信息(如果是卡券类，则以json格式把卡券编号进行存储)',
  `award_type` int(11) DEFAULT NULL COMMENT '奖品类型',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for activity_award_item
-- ----------------------------
DROP TABLE IF EXISTS `activity_award_item`;
CREATE TABLE `activity_award_item` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `item_name` varchar(50) DEFAULT NULL COMMENT '奖项名称',
  `total_num` int(11) DEFAULT NULL COMMENT '奖品数量',
  `probability` float(11,3) DEFAULT NULL COMMENT '概率',
  `status` int(11) DEFAULT NULL COMMENT '奖项状态（0不可用，1可用）',
  `day_total_num` int(11) DEFAULT NULL COMMENT '每天限制发放数',
  `level` int(11) DEFAULT NULL COMMENT '奖项等级',
  `award_id` int(11) DEFAULT NULL COMMENT '奖项对应的奖品编号（外键）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for activity_draw_num
-- ----------------------------
DROP TABLE IF EXISTS `activity_draw_num`;
CREATE TABLE `activity_draw_num` (
  `id` int(11) unsigned NOT NULL,
  `uid` int(11) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  `now_number` int(11) DEFAULT NULL COMMENT '以抽奖次数',
  `max_number` int(11) DEFAULT NULL COMMENT '最大抽奖次数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_UID` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for activity_record
-- ----------------------------
DROP TABLE IF EXISTS `activity_record`;
CREATE TABLE `activity_record` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户idd',
  `name` varchar(50) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `level` int(11) DEFAULT NULL COMMENT '奖品登记',
  `award_name` varchar(255) DEFAULT NULL COMMENT '奖品名称',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
