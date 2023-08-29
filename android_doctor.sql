/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : android_doctor

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 15/08/2023 20:17:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '管理员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (2, 'admin', '123456', '2023-08-05 01:42:24');

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `appointment_id` bigint(20) NOT NULL COMMENT '预约人id',
  `be_appointment_id` bigint(20) NOT NULL COMMENT '预约医生id',
  `appointment_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '预约人姓名',
  `be_appointment_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '预约医生',
  `medical_record` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '就诊记录',
  `prescription` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '处方',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '预约状态',
  `appointment_time` datetime(0) NULL DEFAULT NULL COMMENT '预约时间',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '预约' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of appointment
-- ----------------------------
INSERT INTO `appointment` VALUES (26, 25, 25, '123456', '赵大夫', NULL, NULL, '预约结束', '2023-08-15 05:31:05', '2023-08-15 05:31:05', '2023-08-15 17:29:24');
INSERT INTO `appointment` VALUES (27, 25, 25, '123456', '赵大夫', '1', '1', '预约结束', '2023-08-14 05:32:09', '2023-08-14 05:32:15', '2023-08-14 05:32:18');
INSERT INTO `appointment` VALUES (28, 25, 25, '123456', '赵大夫', '用户于2023年8月处理完成', '刮花奥德赛', '预约结束', '2023-08-15 18:49:09', '2023-08-15 18:49:09', '2023-08-15 18:59:13');
INSERT INTO `appointment` VALUES (29, 25, 25, '123456', '赵大夫', '我那天有事', NULL, '预约失败', '2023-08-15 19:06:02', '2023-08-15 19:06:02', '2023-08-15 19:12:14');
INSERT INTO `appointment` VALUES (30, 25, 25, '123456', '赵大夫', 'test', '1231爱仕达多', '预约结束', '2023-08-15 19:12:27', '2023-08-15 19:12:27', '2023-08-15 19:14:55');
INSERT INTO `appointment` VALUES (31, 36, 29, '张四风', '王五主任', 'xx年xx月xx日处理了什么病人', '药方', '预约结束', '2023-08-15 19:54:06', '2023-08-15 19:54:06', '2023-08-15 19:54:45');
INSERT INTO `appointment` VALUES (32, 37, 30, '病人1', '随便2医生', '我那天没时间', NULL, '预约失败', '2023-08-15 20:06:50', '2023-08-15 20:06:50', '2023-08-15 20:07:16');
INSERT INTO `appointment` VALUES (33, 37, 30, '病人1', '随便2医生', '2023年1月12日处理了什么病人', '刮花', '预约结束', '2023-08-15 20:07:27', '2023-08-15 20:07:27', '2023-08-15 20:08:01');

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '内容',
  `sendName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发送名陈',
  `recvName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '接收名陈',
  `sendId` bigint(20) NULL DEFAULT NULL COMMENT '发送id',
  `recvId` bigint(20) NULL DEFAULT NULL COMMENT '接收id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '聊天' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of chat
-- ----------------------------
INSERT INTO `chat` VALUES (76, '12132', '123456', '赵医生', 25, 25, '2023-08-13 20:57:21');
INSERT INTO `chat` VALUES (77, '1231', '123456', '赵医生', 25, 25, '2023-08-13 21:01:57');
INSERT INTO `chat` VALUES (78, '21312', '123456', '赵医生', 25, 25, '2023-08-14 07:52:19');
INSERT INTO `chat` VALUES (79, '123123', '赵医生', '123456', 25, 25, '2023-08-14 08:21:13');
INSERT INTO `chat` VALUES (80, '123123', '赵医生', '123456', 25, 25, '2023-08-14 08:50:55');
INSERT INTO `chat` VALUES (81, '123', '赵医生', '123456', 25, 25, '2023-08-14 09:02:38');
INSERT INTO `chat` VALUES (82, '1231', '123456', '赵医生', 25, 25, '2023-08-14 09:02:46');
INSERT INTO `chat` VALUES (83, '1312', '赵医生', '123456', 25, 25, '2023-08-14 09:02:57');
INSERT INTO `chat` VALUES (84, '112311', '123456', '赵医生', 25, 25, '2023-08-14 09:11:22');
INSERT INTO `chat` VALUES (85, '[偷笑][偷笑][偷笑]', '赵医生', '123456', 25, 25, '2023-08-14 09:11:41');
INSERT INTO `chat` VALUES (86, '1231', '赵医生', '123456', 25, 25, '2023-08-14 09:12:29');
INSERT INTO `chat` VALUES (87, '213123', '赵医生', '123456', 25, 25, '2023-08-14 09:13:36');
INSERT INTO `chat` VALUES (88, '在吗[可爱]', '张三', '李医生', 34, 26, '2023-08-15 19:21:47');
INSERT INTO `chat` VALUES (89, '在吗', '张三', '李医生', 34, 26, '2023-08-15 19:22:09');
INSERT INTO `chat` VALUES (90, '在吗', '张三', '李医生', 34, 28, '2023-08-15 19:22:21');
INSERT INTO `chat` VALUES (91, '在吗[色]', '张四风', '王五主任', 36, 29, '2023-08-15 19:39:08');
INSERT INTO `chat` VALUES (92, '[色]在点', '王五主任', '张四风', 29, 36, '2023-08-15 19:39:20');
INSERT INTO `chat` VALUES (93, '硕大的', '张四风', '王五主任', 36, 29, '2023-08-15 19:39:44');
INSERT INTO `chat` VALUES (94, '123123', '张四风', '王五主任', 36, 29, '2023-08-15 19:48:43');
INSERT INTO `chat` VALUES (95, 'wddasdasa[呆]', '王五主任', '张四风', 29, 36, '2023-08-15 19:49:02');
INSERT INTO `chat` VALUES (96, '你要看什么病', '王五主任', '张四风', 29, 36, '2023-08-15 19:49:12');
INSERT INTO `chat` VALUES (97, 'wo kanasdasd', '张四风', '王五主任', 36, 29, '2023-08-15 19:49:18');
INSERT INTO `chat` VALUES (98, 'zaima [亲]', '病人1', '随便2医生', 37, 30, '2023-08-15 20:04:26');
INSERT INTO `chat` VALUES (99, '怎么看病点', '病人1', '随便2医生', 37, 30, '2023-08-15 20:05:29');
INSERT INTO `chat` VALUES (100, '你哟啊看什么病', '随便2医生', '病人1', 30, 37, '2023-08-15 20:05:45');
INSERT INTO `chat` VALUES (101, '感冒 ', '病人1', '随便2医生', 37, 30, '2023-08-15 20:05:58');
INSERT INTO `chat` VALUES (102, '好点 我先去预约 然后你去挂科', '随便2医生', '病人1', 30, 37, '2023-08-15 20:06:09');
INSERT INTO `chat` VALUES (103, 'ok', '病人1', '随便2医生', 37, 30, '2023-08-15 20:06:13');

-- ----------------------------
-- Table structure for doctor
-- ----------------------------
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `job_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '唯一医疗号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `contact_information` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系信息',
  `appointment` bit(1) NULL DEFAULT NULL COMMENT '是否预约',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注信息',
  `departments` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '科室',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of doctor
-- ----------------------------
INSERT INTO `doctor` VALUES (25, '11', '111', '123', '赵大夫', '123123', b'1', '工作时间 早8-12', '123', '2023-08-08 17:17:06', '2023-08-14 16:19:55');
INSERT INTO `doctor` VALUES (26, '1691867252564', '1115642310@qq.com', '123', '李四医生', '123', NULL, NULL, '131', '2023-08-13 03:07:33', '2023-08-15 19:23:32');
INSERT INTO `doctor` VALUES (27, '1691867269008', '11156423210@qq.com', '123', '张医生', '123', NULL, NULL, '123', '2023-08-13 03:07:49', '2023-08-13 03:15:28');
INSERT INTO `doctor` VALUES (28, '1692098366837', '123456@qq.com', '123456', '李三医生', '123456', NULL, '白天8-12点 1-7点', '医生科', '2023-08-15 19:19:27', '2023-08-15 19:23:26');
INSERT INTO `doctor` VALUES (29, '1692099338519', '12356@qq.com', '123456', '王五主任', '123456', b'1', '早8-12,中1-7', '脑科', '2023-08-15 19:35:39', '2023-08-15 19:53:47');
INSERT INTO `doctor` VALUES (30, '1692100979302', '21@qq.com', '123', '随便2医生', '随便', b'1', '早上8-12 中午1-5', '脑科', '2023-08-15 20:02:59', '2023-08-15 20:06:34');

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `medical_treatment_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '唯一医疗号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `contact_information` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系信息',
  `birthday` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生日',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `un`(`medical_treatment_no`) USING BTREE COMMENT '唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of patient
-- ----------------------------
INSERT INTO `patient` VALUES (25, '1', '111', '123', '123456', 10.00, '12313', '', '2023-08-07 20:47:05', '2023-08-07 20:47:05');
INSERT INTO `patient` VALUES (28, '1691777611325', '234', '123456', 'test', NULL, '123456789', '2001-01-03', '2023-08-12 02:13:39', '2023-08-12 02:13:39');
INSERT INTO `patient` VALUES (29, '1691777650686', '12313465221@qq.com', '123456', 'test', NULL, '123456789', '2001-01-03', '2023-08-12 02:14:11', '2023-08-12 02:14:11');
INSERT INTO `patient` VALUES (30, '1691777655024', '123134621@qq.com', '123456', 'test', NULL, '123456789', '2001-01-03', '2023-08-12 02:14:15', '2023-08-12 02:14:15');
INSERT INTO `patient` VALUES (31, '1691818426330', '123134652121@qq.com', '123456', 'test', NULL, '123456789', '2001-01-03', '2023-08-12 13:33:46', '2023-08-12 13:33:46');
INSERT INTO `patient` VALUES (32, '1691818476923', '1231341236521@qq.com', '123456', 'test', NULL, '123456789', '2001-01-03', '2023-08-12 13:34:37', '2023-08-12 13:34:37');
INSERT INTO `patient` VALUES (33, '1691818525666', '121131346521@qq.com', '123456', 'test', NULL, '123456789', '2001-01-03', '2023-08-12 13:35:26', '2023-08-12 13:35:26');
INSERT INTO `patient` VALUES (34, '1692098458758', '111@qq.com', '123456', '张三', NULL, '123456789', '2001-01-03', '2023-08-15 19:20:59', '2023-08-15 19:20:59');
INSERT INTO `patient` VALUES (35, '1692099433360', '1231346521@qq.com', '123456', 'test', NULL, '123456789', '2001-01-03', '2023-08-15 19:37:13', '2023-08-15 19:37:13');
INSERT INTO `patient` VALUES (36, '1692099464429', '11@qq.com', '123456', '张四风', NULL, '123456789', '2001-01-03', '2023-08-15 19:37:44', '2023-08-15 19:38:33');
INSERT INTO `patient` VALUES (37, '1692101044215', '1@qq.com', '123456', '病人1', NULL, '123456789', '2001-01-03', '2023-08-15 20:04:04', '2023-08-15 20:04:04');

SET FOREIGN_KEY_CHECKS = 1;
