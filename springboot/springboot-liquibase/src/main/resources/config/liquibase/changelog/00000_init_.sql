--liquibase formatted sql
--changeset whx:1.1
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for test_user_tab
-- ----------------------------
DROP TABLE IF EXISTS `test_user_tab`;
CREATE TABLE `test_user_tab` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `userAccount` varchar(16) NOT NULL,
  `password` varchar(32) NOT NULL,
  `userStatus` tinyint(1) NOT NULL DEFAULT '1',
  `addTime` datetime NOT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试用户表';

--changeset whx:1.2
ALTER TABLE `test_user_tab`
DROP COLUMN `addTime`;
--rollback ALTER TABLE `test_user_tab` ADD COLUMN `addTime`  datetime NOT NULL AFTER `userStatus`;

--changeset whx:1.3

ALTER TABLE `test_user_tab`
ADD COLUMN `addTime`  datetime NOT NULL AFTER `userStatus`;
--rollback ALTER TABLE `test_user_tab` DROP COLUMN `addTime`;

--changeset whx:1.4
ALTER TABLE `test_user_tab`
DROP COLUMN `addTime`;
--rollback ALTER TABLE `test_user_tab` ADD COLUMN `addTime`  datetime NOT NULL AFTER `userStatus`;

--changeset whx:1.5
ALTER TABLE `test_user_tab`
ADD COLUMN `addTime`  datetime NOT NULL AFTER `userStatus`;
--rollback ALTER TABLE `test_user_tab` DROP COLUMN `addTime`;
