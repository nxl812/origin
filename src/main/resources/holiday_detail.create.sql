CREATE TABLE `holiday_detail` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `start_day` varchar(20) NOT NULL DEFAULT '' COMMENT 'yyyyMMdd格式的',
  `day_range` int(3) NOT NULL DEFAULT '0',
  `type` int(1) NOT NULL DEFAULT '1' COMMENT '1是节假日\r\n2是调休补班的',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_disable` int(1) NOT NULL DEFAULT '0' COMMENT '1是失效0是有用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8