/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80014
Source Host           : localhost:3306
Source Database       : springcloud

Target Server Type    : MYSQL
Target Server Version : 80014
File Encoding         : 65001

Date: 2019-10-18 15:47:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for server_manager
-- ----------------------------
DROP TABLE IF EXISTS `server_manager`;
CREATE TABLE `server_manager` (
  `username` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '服务器用户名',
  `userpwd` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '服务器密码',
  `ip` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ip地址',
  `port` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务端口号',
  `filepath` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '要执行的脚本路径',
  `servicename` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '服务名',
  `execustatus` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '执行状态（1、可执行，2、不可执行）',
  `rmk` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`ip`,`port`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
