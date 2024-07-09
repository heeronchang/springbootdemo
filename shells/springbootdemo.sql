/*
 Navicat Premium Data Transfer

 Source Server         : docker
 Source Server Type    : MySQL
 Source Server Version : 80200 (8.2.0)
 Source Host           : 127.0.0.1:3306
 Source Schema         : springbootdemo

 Target Server Type    : MySQL
 Target Server Version : 80200 (8.2.0)
 File Encoding         : 65001

 Date: 02/07/2024 19:49:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `USERNAME_UNIQUE`(`username` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'jone', '$2a$10$CCsZdQ3mKSuatfMvTVGbReYUkSrmRb87c8XGTDPRqDREPprZnHBEO', 'Jone', 18, 'test1@baomidou.com');
INSERT INTO `user` VALUES (2, 'jack', '$2a$10$CCsZdQ3mKSuatfMvTVGbReYUkSrmRb87c8XGTDPRqDREPprZnHBEO', 'Jack', 20, 'test2@baomidou.com');
INSERT INTO `user` VALUES (3, 'tom', '$2a$10$CCsZdQ3mKSuatfMvTVGbReYUkSrmRb87c8XGTDPRqDREPprZnHBEO', 'Tom', 28, 'test3@baomidou.com');
INSERT INTO `user` VALUES (4, 'sandy', '$2a$10$CCsZdQ3mKSuatfMvTVGbReYUkSrmRb87c8XGTDPRqDREPprZnHBEO', 'Sandy', 21, 'test4@baomidou.com');
INSERT INTO `user` VALUES (5, 'billie', '$2a$10$CCsZdQ3mKSuatfMvTVGbReYUkSrmRb87c8XGTDPRqDREPprZnHBEO', 'Billie', 24, 'test5@baomidou.com');

SET FOREIGN_KEY_CHECKS = 1;
