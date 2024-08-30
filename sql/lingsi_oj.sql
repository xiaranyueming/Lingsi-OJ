/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80038 (8.0.38)
 Source Host           : localhost:3306
 Source Schema         : lingsi_oj

 Target Server Type    : MySQL
 Target Server Version : 80038 (8.0.38)
 File Encoding         : 65001

 Date: 30/08/2024 10:02:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '索引',
  `title` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `tags` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签',
  `answer` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '题目答案',
  `judge_case` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '判题用例',
  `judge_config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '判题配置',
  `submit_num` int NULL DEFAULT 0 COMMENT '提交数',
  `accept_num` int NULL DEFAULT 0 COMMENT '通过数',
  `user_id` int NOT NULL COMMENT '创建用户id',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES (1, 'A + B', '计算两数之和', '[\"简单\", \"算术\"]', NULL, NULL, NULL, 0, 0, 1, '2024-07-30 15:27:24', '2024-08-02 14:20:49', 1);
INSERT INTO `question` VALUES (2, '两数之和', '计算A,B两数之和', '[\"简单\"]', '5', '[{\"input\":\"2,3\",\"output\":\"5\"}]', '{\"timeLimit\":0,\"memoryLimit\":0,\"stackLimit\":0}', 0, 0, 1, '2024-07-31 11:06:39', '2024-08-02 14:21:00', 1);
INSERT INTO `question` VALUES (3, '两数之和', '计算A,B两数之和', '[\"简单\"]', '5', '[{\"input\":\"2,3\",\"output\":\"5\"},{\"input\":\"4,9\",\"output\":\"13\"}]', '{\"timeLimit\":0,\"memoryLimit\":0,\"stackLimit\":0}', 0, 0, 1, '2024-07-31 11:12:00', '2024-08-02 14:21:25', 1);
INSERT INTO `question` VALUES (4, '两数之和', '计算A,B两数之和', '[\"简单\"]', '5', '[{\"input\":\"2,3\",\"output\":\"5\"}]', '{\"timeLimit\":0,\"memoryLimit\":0,\"stackLimit\":0}', 0, 0, 1, '2024-07-31 11:32:58', '2024-08-02 14:24:19', 1);
INSERT INTO `question` VALUES (5, '两数之和', '计算A,B两数之和', '[\"简单\"]', '5', '[{\"input\":\"2,3\",\"output\":\"5\"}]', '{\"timeLimit\":0,\"memoryLimit\":0,\"stackLimit\":0}', 2, 0, 1, '2024-07-31 11:35:46', '2024-07-31 11:35:46', 0);
INSERT INTO `question` VALUES (6, '两数之和', '计算A,B两数之和', '[\"简单\"]', '5', '[{\"input\":\"2,3\",\"output\":\"5\"}]', '{\"timeLimit\":0,\"memoryLimit\":0,\"stackLimit\":0}', 3, 0, 1, '2024-07-31 11:35:47', '2024-07-31 11:35:47', 0);
INSERT INTO `question` VALUES (7, '两数之和', '计算A,B两数之和', '[\"简单\"]', '5', '[{\"input\":\"2,3\",\"output\":\"5\"}]', '{\"timeLimit\":0,\"memoryLimit\":0,\"stackLimit\":0}', 1, 0, 1, '2024-07-31 11:38:34', '2024-07-31 11:38:34', 0);
INSERT INTO `question` VALUES (8, '回文数', '## 概述\n若一个数从前向后看和从后向前看一致则可以称其为回文数', '[\"字符串\",\"简单\"]', 'true', '[{\"input\":\"121\",\"output\":\"true\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1024,\"stackLimit\":1024}', 1, 0, 1, '2024-08-01 15:21:49', '2024-08-01 15:21:49', 0);
INSERT INTO `question` VALUES (9, '计算 a+b', '# 计算两数之和\n\n## 输入示例\n\n1， 2\n\n## 输出示例\n\n3', '[\"简单\"]', '3', '[{\"input\":\"1 2\",\"output\":\"3\"},{\"input\":\"2 5\",\"output\":\"7\"}]', '{\"timeLimit\":1000,\"memoryLimit\":1024,\"stackLimit\":1024}', 9, 0, 1, '2024-08-21 08:46:08', '2024-08-21 08:46:08', 0);

-- ----------------------------
-- Table structure for question_submit
-- ----------------------------
DROP TABLE IF EXISTS `question_submit`;
CREATE TABLE `question_submit`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '索引',
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '代码',
  `language` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编程语言',
  `question_id` int NOT NULL COMMENT '题目id',
  `user_id` int NOT NULL COMMENT '提交题目的用户id',
  `status` int NULL DEFAULT 0 COMMENT '判题状态',
  `judge_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '判题信息',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '题目提交表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of question_submit
-- ----------------------------
INSERT INTO `question_submit` VALUES (1, 'public int sum(int a, int b) { return a + b; }', 'java', 1, 1, 0, NULL, '2024-07-31 15:10:35', '2024-08-08 13:33:29', 1);
INSERT INTO `question_submit` VALUES (2, 'public int sum(int a, int b) {\r\n    return a+b;\r\n}', 'java', 8, 1, 0, NULL, '2024-08-03 08:39:08', '2024-08-03 08:39:08', 0);
INSERT INTO `question_submit` VALUES (3, 'int sum(int a, int b) {\r\n    return a+b;\r\n}', 'java', 5, 1, 1, NULL, '2024-08-08 10:19:18', '2024-08-08 10:19:18', 0);
INSERT INTO `question_submit` VALUES (4, '111111', 'java', 5, 1, 1, NULL, '2024-08-08 10:23:51', '2024-08-08 10:23:51', 0);
INSERT INTO `question_submit` VALUES (5, 'ddddddd', 'java', 7, 1, 1, NULL, '2024-08-08 10:27:57', '2024-08-08 10:27:57', 0);
INSERT INTO `question_submit` VALUES (6, 'sssssssss', 'java', 6, 1, 1, NULL, '2024-08-08 10:49:12', '2024-08-08 10:49:12', 0);
INSERT INTO `question_submit` VALUES (7, 'sssssssss', 'java', 6, 1, 1, NULL, '2024-08-08 10:49:51', '2024-08-08 10:49:51', 0);
INSERT INTO `question_submit` VALUES (8, 'sssssssss', 'java', 6, 1, 2, '{\"message\":\"时间超限\",\"memory\":500,\"time\":500}', '2024-08-08 10:53:50', '2024-08-08 10:53:51', 0);
INSERT INTO `question_submit` VALUES (9, 'public class Main {\r\n    public static void main(String[] args) {\r\n        int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n         \r\n        System.out.println(a + b);\r\n    }\r\n}', 'java', 9, 1, 2, '{\"message\":\"答案错误\",\"time\":48}', '2024-08-21 08:46:47', '2024-08-21 08:46:49', 0);
INSERT INTO `question_submit` VALUES (10, 'public class Main {\r\n    public static void main(String[] args) {\r\n        int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n\r\n        System.out.println(a + b);\r\n    }\r\n}', 'java', 9, 1, 2, '{\"message\":\"答案错误\",\"time\":30}', '2024-08-21 08:51:47', '2024-08-21 08:51:48', 0);
INSERT INTO `question_submit` VALUES (11, 'public class Main {\r\n    public static void main(String[] args) {\r\n        int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n\r\n        System.out.println(a + b);\r\n    }\r\n}', 'java', 9, 1, 1, NULL, '2024-08-21 08:54:01', '2024-08-21 08:54:01', 0);
INSERT INTO `question_submit` VALUES (12, 'public class Main {\r\n    public static void main(String[] args) {\r\n        int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n\r\n        System.out.println(a + b);\r\n    }\r\n}', 'java', 9, 1, 1, NULL, '2024-08-21 08:55:45', '2024-08-21 08:55:45', 0);
INSERT INTO `question_submit` VALUES (13, 'public class Main {\r\n    public static void main(String[] args) {\r\n        int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n\r\n        System.out.println(a + b);\r\n    }\r\n}', 'java', 9, 1, 1, NULL, '2024-08-21 08:57:00', '2024-08-21 08:57:00', 0);
INSERT INTO `question_submit` VALUES (14, 'public class Main {\r\n    public static void main(String[] args) {\r\n        int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n\r\n        System.out.println(a + b);\r\n    }\r\n}', 'java', 9, 1, 1, NULL, '2024-08-21 09:02:02', '2024-08-21 09:02:02', 0);
INSERT INTO `question_submit` VALUES (15, 'public class Main {\r\n    public static void main(String[] args) {\r\n        int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n\r\n        System.out.println(a + b);\r\n    }\r\n}', 'java', 9, 1, 1, NULL, '2024-08-21 09:02:41', '2024-08-21 09:02:41', 0);
INSERT INTO `question_submit` VALUES (16, 'public class Main {\r\n    public static void main(String[] args) {\r\n        int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n\r\n        System.out.println(a + b);\r\n    }\r\n}', 'java', 9, 1, 1, NULL, '2024-08-21 09:04:34', '2024-08-21 09:04:34', 0);
INSERT INTO `question_submit` VALUES (17, 'public class Main {\r\n    public static void main(String[] args) {\r\n        int a = Integer.parseInt(args[0]);\r\n        int b = Integer.parseInt(args[1]);\r\n\r\n        System.out.println(a + b);\r\n    }\r\n}', 'java', 9, 1, 2, '{\"memory\":0,\"time\":71}', '2024-08-21 09:07:14', '2024-08-21 09:07:15', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'https://tse3-mm.cn.bing.net/th/id/OIP-C.SenEuZIdVibCwBw5wQJS_gAAAA?rs=1&pid=ImgDetMain' COMMENT '用户头像',
  `description` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户描述',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色：user，admin',
  `create_time` datetime NOT NULL DEFAULT (now()) COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_name_unique`(`user_name` ASC) USING BTREE COMMENT '用户名唯一'
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zhang', '698d51a19d8a121ce581499d7b701668', 'http://localhost:8080/upload/1724201058564.jpg', '111222', 'admin', '2024-07-29 09:43:24', '2024-08-21 08:44:20', 0);
INSERT INTO `user` VALUES (2, 'li', 'e10adc3949ba59abbe56e057f20f883e', 'https://tse3-mm.cn.bing.net/th/id/OIP-C.SenEuZIdVibCwBw5wQJS_gAAAA?rs=1&pid=ImgDetMain', NULL, 'user', '2024-07-29 15:03:54', '2024-07-29 15:03:54', 0);
INSERT INTO `user` VALUES (3, '11111', 'b0baee9d279d34fa1dfd71aadb908c3f', 'https://tse3-mm.cn.bing.net/th/id/OIP-C.SenEuZIdVibCwBw5wQJS_gAAAA?rs=1&pid=ImgDetMain', NULL, 'user', '2024-07-29 15:46:36', '2024-07-29 15:46:36', 0);

SET FOREIGN_KEY_CHECKS = 1;
