/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80011 (8.0.11)
 Source Host           : localhost:3306
 Source Schema         : cwyy_qh

 Target Server Type    : MySQL
 Target Server Version : 80011 (8.0.11)
 File Encoding         : 65001

 Date: 19/08/2024 23:22:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qh_account
-- ----------------------------
DROP TABLE IF EXISTS `qh_account`;
CREATE TABLE `qh_account`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL COMMENT '1. Administrator 2. User 3. Doctor',
  `status` int(11) NULL DEFAULT NULL COMMENT '1. Enable 2. Disable',
  `url` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 158 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qh_account
-- ----------------------------


-- ----------------------------
-- Table structure for qh_appoint
-- ----------------------------
DROP TABLE IF EXISTS `qh_appoint`;
CREATE TABLE `qh_appoint`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NULL DEFAULT NULL,
  `doctid` int(11) NULL DEFAULT NULL,
  `apppointtime` datetime NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL COMMENT '1. Success 2. Cancel 3. Expired',
  `sxwu` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 247 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qh_appoint
-- ----------------------------

-- ----------------------------
-- Table structure for qh_disease
-- ----------------------------
DROP TABLE IF EXISTS `qh_disease`;
CREATE TABLE `qh_disease`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `symptom` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'symptom',
  `proposal` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'suggestion',
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qh_disease
-- ----------------------------

-- ----------------------------
-- Table structure for qh_medicine
-- ----------------------------
DROP TABLE IF EXISTS `qh_medicine`;
CREATE TABLE `qh_medicine`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Instruction` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Instructions for use',
  `treatment` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Treatment Effects',
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `stock` int(11) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qh_medicine
-- ----------------------------

-- ----------------------------
-- Table structure for qh_order
-- ----------------------------
DROP TABLE IF EXISTS `qh_order`;
CREATE TABLE `qh_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `petid` int(11) NULL DEFAULT NULL,
  `userid` int(11) NULL DEFAULT NULL,
  `doctid` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL COMMENT '1. To be paid 2. Paid 3. Expired',
  `sumprice` decimal(10, 2) NULL DEFAULT NULL,
  `dis1` int(11) NULL DEFAULT NULL,
  `dis2` int(11) NULL DEFAULT NULL,
  `dis3` int(11) NULL DEFAULT NULL,
  `dis4` int(11) NULL DEFAULT NULL,
  `dis5` int(11) NULL DEFAULT NULL,
  `med1` int(11) NULL DEFAULT NULL,
  `med2` int(11) NULL DEFAULT NULL,
  `med3` int(11) NULL DEFAULT NULL,
  `med4` int(11) NULL DEFAULT NULL,
  `med5` int(11) NULL DEFAULT NULL,
  `med6` int(11) NULL DEFAULT NULL,
  `med7` int(11) NULL DEFAULT NULL,
  `med8` int(11) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `jy` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qh_order
-- ----------------------------

-- ----------------------------
-- Table structure for qh_pet
-- ----------------------------
DROP TABLE IF EXISTS `qh_pet`;
CREATE TABLE `qh_pet`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `classtype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL COMMENT '1. Healthy 2. Unhealthy',
  `url` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qh_pet
-- ----------------------------

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'uuid',
  `code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'Verification Code',
  `expire_time` datetime NULL DEFAULT NULL COMMENT 'Expiration time',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'System verification code' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'token',
  `expire_time` datetime NULL DEFAULT NULL COMMENT 'Expiration time',
  `update_time` datetime NULL DEFAULT NULL COMMENT 'Update time',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `token`(`token` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'System User Token' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------

-- ----------------------------
-- Procedure structure for p1
-- ----------------------------
DROP PROCEDURE IF EXISTS `p1`;
delimiter ;;
CREATE PROCEDURE `p1`()
BEGIN
	SELECT id,name FROM qh_medicine;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for p2
-- ----------------------------
DROP PROCEDURE IF EXISTS `p2`;
delimiter ;;
CREATE PROCEDURE `p2`()
BEGIN
	SELECT id,name FROM qh_disease;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for p3
-- ----------------------------
DROP PROCEDURE IF EXISTS `p3`;
delimiter ;;
CREATE PROCEDURE `p3`(IN input_param INT)
BEGIN
    SELECT id, name FROM qh_pet WHERE userid = (select account from qh_account where id =input_param);
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
