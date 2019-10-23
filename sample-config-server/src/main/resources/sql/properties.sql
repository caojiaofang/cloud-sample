CREATE TABLE `properties` (
  `id` int(11) NOT NULL,
  `akey` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `avalue` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `application` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `aprofile` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `lable` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of properties
-- ----------------------------
INSERT INTO `properties` VALUES ('1', 'json-spring.datasource', '{\"password\":\"123456\",\"driver-class-name\":\"com.mysql.cj.jdbc.Driver\",\"url\":\"jdbc:mysql://127.0.0.1:3306/springcloud\",\"username\":\"root\"}', 'application', 'dev', 'master');
