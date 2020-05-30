CREATE TABLE `device`
(
    `id`     bigint(20) NOT NULL AUTO_INCREMENT,
    `zname`  varchar(45) DEFAULT NULL,
    `gxdw`   varchar(45) DEFAULT NULL,
    `sbname` varchar(45) DEFAULT NULL,
    `sbtype` varchar(45) DEFAULT NULL,
    `xb`     varchar(45) DEFAULT NULL,
    `tc`     varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 14
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `employee`
(
    `id`     bigint(20) NOT NULL AUTO_INCREMENT,
    `emname` varchar(45) DEFAULT NULL,
    `zw`     varchar(45) DEFAULT NULL,
    `dw`     varchar(45) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 22
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `repair`
(
    `id`        bigint(20) NOT NULL AUTO_INCREMENT,
    `sbid`      bigint(20)   DEFAULT NULL,
    `jxcontent` varchar(512) DEFAULT NULL,
    `jxdate`    datetime     DEFAULT NULL,
    `jxren`     varchar(128) DEFAULT NULL,
    `jxtype`    varchar(64)  DEFAULT NULL,
    `xj`        tinyint(4)   DEFAULT NULL,
    `zn`        varchar(45)  DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 129
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `wt`
(
    `id`       bigint(20) NOT NULL AUTO_INCREMENT,
    `sbid`     bigint(20)   DEFAULT NULL,
    `wtdate`   datetime     DEFAULT NULL,
    `wtscript` varchar(512) DEFAULT NULL,
    `xjdate`   datetime     DEFAULT NULL,
    `xj`       tinyint(4)   DEFAULT '0',
    PRIMARY KEY (`id`),
    UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 58
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci

CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci






