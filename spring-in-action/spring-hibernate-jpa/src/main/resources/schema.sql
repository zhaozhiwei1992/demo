
-- 现在直接使用 hibernate生成
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL,
  `age` int(11) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
