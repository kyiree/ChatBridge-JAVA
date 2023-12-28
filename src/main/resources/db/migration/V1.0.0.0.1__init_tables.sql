USE `chatbridge`;
CREATE TABLE IF NOT EXISTS `tb_exchange`
(
    `id`              BIGINT AUTO_INCREMENT COMMENT '兑换码ID' PRIMARY KEY,
    `code`            VARCHAR(8) NOT NULL COMMENT '兑换码',
    `frequency`       BIGINT     NOT NULL COMMENT '兑换码所含Ai币',
    `created_time`    DATETIME   NOT NULL COMMENT '创建时间',
    `update_time`     DATETIME   NOT NULL COMMENT '修改时间',
    `created_user_id` BIGINT     NOT NULL COMMENT '创建者id',
    `update_user_id`  BIGINT     NOT NULL COMMENT '编辑者id',
    CONSTRAINT `idx_tb_exchange_code` UNIQUE (`code`)
);

CREATE TABLE IF NOT EXISTS `tb_order`
(
    `id`              BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    `order_num`       VARCHAR(128)          NOT NULL,
    `user_id`         BIGINT                NOT NULL,
    `product_id`      BIGINT                NOT NULL,
    `product_name`    VARCHAR(64)           NOT NULL,
    `product_price`   DOUBLE                NOT NULL,
    `state`           TINYINT               NOT NULL,
    `pay_time`        DATETIME,
    `frequency`       BIGINT                NOT NULL,
    `reason_failure`  VARCHAR(64),
    `created_time`    DATETIME              NOT NULL,
    `update_time`     DATETIME              NOT NULL,
    `created_user_id` BIGINT                NOT NULL COMMENT '创建者id',
    `update_user_id`  BIGINT                NOT NULL COMMENT '编辑者id',
    UNIQUE `idx_tb_order_order_num` (`order_num`)
);

CREATE TABLE IF NOT EXISTS `tb_personality`
(
    `id`              BIGINT AUTO_INCREMENT COMMENT '主键ID' PRIMARY KEY,
    `user_id`         BIGINT       NOT NULL COMMENT '所属用户',
    `model`           VARCHAR(64)  NOT NULL COMMENT '模型名称',
    `top_p`           TINYINT      NOT NULL,
    `max_tokens`      BIGINT       NOT NULL COMMENT '上传图',
    `temperature`     TINYINT      NOT NULL COMMENT '生成图',
    `open_key`        VARCHAR(256) NOT NULL COMMENT 'chatgpt密钥',
    `open_ai_url`     VARCHAR(256) NOT NULL COMMENT 'chatgpt请求地址',
    `questions`       TEXT         NOT NULL COMMENT '问题',
    `answer`          TEXT         NOT NULL COMMENT '回答',
    `speed`           BIGINT       NOT NULL COMMENT '回复速率',
    `created_time`    DATETIME     NOT NULL COMMENT '创建时间',
    `update_time`     DATETIME     NOT NULL COMMENT '修改时间',
    `created_user_id` BIGINT       NOT NULL COMMENT '创建者id',
    `update_user_id`  BIGINT       NOT NULL COMMENT '编辑者id',
    UNIQUE `idx_tb_personality_user_id` (`user_id`)
);

CREATE TABLE IF NOT EXISTS `tb_product`
(
    `id`              BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`            VARCHAR(128) NOT NULL,
    `frequency`       BIGINT       NOT NULL,
    `price`           DOUBLE       NOT NULL,
    `created_time`    DATETIME     NOT NULL,
    `update_time`     DATETIME     NOT NULL,
    `created_user_id` BIGINT       NOT NULL COMMENT '创建者id',
    `update_user_id`  BIGINT       NOT NULL COMMENT '编辑者id'
);

CREATE TABLE IF NOT EXISTS `tb_star`
(
    `id`              BIGINT AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    `user_id`         BIGINT   NOT NULL COMMENT '所属用户',
    `issue`           LONGTEXT NOT NULL COMMENT '问题',
    `answer`          LONGTEXT NOT NULL COMMENT '答案',
    `created_time`    DATETIME NOT NULL COMMENT '创建时间',
    `update_time`     DATETIME NOT NULL COMMENT '修改时间',
    `created_user_id` BIGINT   NOT NULL COMMENT '创建者id',
    `update_user_id`  BIGINT   NOT NULL COMMENT '编辑者id'
);

CREATE TABLE IF NOT EXISTS `tb_user`
(
    `id`              BIGINT AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    `open_id`         VARCHAR(256) COMMENT '微信用户标识',
    `avatar`          VARCHAR(256) COMMENT '用户头像',
    `user_name`       VARCHAR(128) COMMENT '用户微信昵称',
    `email`           VARCHAR(256),
    `password`        VARCHAR(256),
    `type`            VARCHAR(32)       NOT NULL,
    `frequency`       BIGINT  DEFAULT 0 NOT NULL COMMENT 'Ai币',
    `is_sign_in`      TINYINT DEFAULT 0 NOT NULL COMMENT '是否签到',
    `created_time`    DATETIME          NOT NULL COMMENT '创建时间',
    `update_time`     DATETIME          NOT NULL COMMENT '修改时间',
    `created_user_id` BIGINT            NOT NULL COMMENT '创建者id',
    `update_user_id`  BIGINT            NOT NULL COMMENT '编辑者id',
    UNIQUE `idx_tb_user_open_id` (`open_id`)
);
INSERT INTO `tb_user`(id, open_id, avatar, user_name, email, password, type, frequency, is_sign_in, created_time,
                      update_time, created_user_id, update_user_id)
VALUES (1, null, null, 'admin', '', '', 'ADMIN', '999', 0, now(), now(), 1, 1);

CREATE TABLE IF NOT EXISTS `tb_system_setting`
(
    `id`              BIGINT AUTO_INCREMENT COMMENT '主键' PRIMARY KEY,
    `type`            VARCHAR(32) NOT NULL COMMENT '系统配置类型',
    `name`            VARCHAR(64) NOT NULL,
    `value`           VARCHAR(128),
    `created_time`    DATETIME    NOT NULL COMMENT '创建时间',
    `update_time`     DATETIME    NOT NULL COMMENT '修改时间',
    `created_user_id` BIGINT      NOT NULL COMMENT '创建者id',
    `update_user_id`  BIGINT      NOT NULL COMMENT '编辑者id',
    UNIQUE `idx_tb_system_setting_type_key` (`type`, `name`)
);

INSERT INTO `tb_system_setting`(`id`, `type`, `name`, `value`, `created_time`, `update_time`, `created_user_id`,
                                `update_user_id`)
VALUES (null, 'ANNOUNCEMENT', 'context', NULL, now(), now(), 1, 1),
       (null, 'ANNOUNCEMENT', 'logotypeId', NULL, now(), now(), 1, 1),
       (null, 'ANNOUNCEMENT', 'createdTime', NULL, now(), now(), 1, 1),
       (null, 'OPEN_AI_CONFIG', 'openAiPlusUrl', 'https://api.openai.com/v1', now(), now(), 1, 1),
       (null, 'OPEN_AI_CONFIG', 'openPlusKey', '', now(), now(), 1, 1),
       (null, 'OPEN_AI_CONFIG', 'gptPlusFrequency', '1', now(), now(), 1, 1),
       (null, 'BOT_CONFIG', 'botNameChinese', 'ChatBridge', now(), now(), 1, 1),
       (null, 'BOT_CONFIG', 'botNameEnglish', 'ChatBridge', now(), now(), 1, 1),
       (null, 'BOT_CONFIG', 'author', 'Kyire', now(), now(), 1, 1),
       (null, 'INSPIRIT_CONFIG', 'incentiveFrequency', 10, now(), now(), 1, 1),
       (null, 'INSPIRIT_CONFIG', 'signInFrequency', 1, now(), now(), 1, 1),
       (null, 'INSPIRIT_CONFIG', 'videoFrequency', 1, now(), now(), 1, 1),
       (null, 'PROXY_CONFIG', 'enable', 'ENABLE', now(), now(), 1, 1),
       (null, 'PROXY_CONFIG', 'proxyIp', '127.0.0.1', now(), now(), 1, 1),
       (null, 'PROXY_CONFIG', 'proxyPort', '7890', now(), now(), 1, 1),
       (null, 'WECHAT_CONFIG', 'appId', NULL, now(), now(), 1, 1),
       (null, 'WECHAT_CONFIG', 'secret', NULL, now(), now(), 1, 1),
       (null, 'ALI_OSS_CONFIG', 'endpoint', NULL, now(), now(), 1, 1),
       (null, 'ALI_OSS_CONFIG', 'accessKey', NULL, now(), now(), 1, 1),
       (null, 'ALI_OSS_CONFIG', 'secretKey', NULL, now(), now(), 1, 1),
       (null, 'ALI_OSS_CONFIG', 'bucketName', NULL, now(), now(), 1, 1),
       (null, 'ALI_OSS_CONFIG', 'domain', NULL, now(), now(), 1, 1),
       (null, 'ALI_PAY_CONFIG', 'appId', NULL, now(), now(), 1, 1),
       (null, 'ALI_PAY_CONFIG', 'publicKey', NULL, now(), now(), 1, 1),
       (null, 'ALI_PAY_CONFIG', 'privateKey', NULL, now(), now(), 1, 1),
       (null, 'ALI_PAY_CONFIG', 'domain', NULL, now(), now(), 1, 1),
       (null, 'MAIL_CONFIG', 'host', NULL, now(), now(), 1, 1),
       (null, 'MAIL_CONFIG', 'username', NULL, now(), now(), 1, 1),
       (null, 'MAIL_CONFIG', 'password', NULL, now(), now(), 1, 1),
       (null, 'MAIL_CONFIG', 'port', NULL, now(), now(), 1, 1);







