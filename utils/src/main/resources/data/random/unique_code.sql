CREATE TABLE `hardware_unique_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `unique_code` varchar(14) NOT NULL COMMENT '唯一码，固定14长度',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '0表示智能体温计，1表示排卵试纸，2表示基础体温计',
  `batch` int(11) NOT NULL DEFAULT '0' COMMENT '批次',
  `used` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0表示没有被使用过，1表示被使用过',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique` (`unique_code`) USING BTREE,
  KEY `index2` (`type`,`batch`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4001 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `hardware_unique_code_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch` int(11) NOT NULL DEFAULT '0' COMMENT '批次',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '同hardware_unique_code的type',
  `number` int(11) DEFAULT '0' COMMENT '每次增加了多少个',
  `max_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '不通type的最大id',
  `gmt_create_time` bigint(20) NOT NULL,
  `gmt_update_time` bigint(20) NOT NULL,
  `creator` varchar(20) DEFAULT NULL COMMENT '操作人',
  `updater` varchar(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  KEY `index` (`batch`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

