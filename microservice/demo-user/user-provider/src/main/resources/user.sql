/*
Navicat MySQL Data Transfer

Source Server         : 192.168.11.156
Source Server Version : 50719
Source Host           : 192.168.11.156:3306
Source Database       : user

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-06-30 22:13:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(80) DEFAULT NULL COMMENT '用户名（登录）',
  `password` varchar(128) DEFAULT NULL COMMENT '密码（登录）',
  `realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `avatar` varchar(300) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `sex` varchar(2) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '状态（1正常，2冻结）',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USERNAME` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'Mic', null, '13073804251', '男', '1', '2017-09-04 16:32:15');
