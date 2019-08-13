-- MariaDB dump 10.17  Distrib 10.4.7-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: user
-- ------------------------------------------------------
-- Server version	10.4.7-MariaDB

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) DEFAULT NULL COMMENT '用户名(登录)',
  `password` varchar(128) DEFAULT NULL COMMENT '密码（登录）',
  `realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `avatar` varchar(300) DEFAULT NULL COMMENT '头像',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `sex` varchar(10) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '状态（1正常，2冻结）',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp() COMMENT '创建时间',
  `age` int(11) DEFAULT NULL,
  `real_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_USERNAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

