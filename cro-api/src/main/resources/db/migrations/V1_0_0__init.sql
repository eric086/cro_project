-- ----------------------------
-- Table structure for tbl_chat
-- ----------------------------
CREATE TABLE `tbl_chat` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT,
                            `project_id` int NOT NULL COMMENT '项目ID',
                            `title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
                            `number` int NOT NULL COMMENT '知识范围数量',
                            `create_by` int NOT NULL COMMENT '创建者',
                            `create_at` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            PRIMARY KEY (`id`),
                            KEY `i_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for tbl_chat_info
-- ----------------------------
CREATE TABLE `tbl_chat_info` (
                                 `id` int unsigned NOT NULL AUTO_INCREMENT,
                                 `chat_id` int NOT NULL COMMENT '问答ID',
                                 `ask` text COLLATE utf8mb4_general_ci COMMENT '问题',
                                 `answer` text COLLATE utf8mb4_general_ci COMMENT '回答',
                                 `questioner` int DEFAULT NULL COMMENT '提问者',
                                 `create_at` timestamp DEFAULT NULL COMMENT '提问时间',
                                 PRIMARY KEY (`id`),
                                 KEY `i_chat_id` (`chat_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for tbl_file
-- ----------------------------
CREATE TABLE `tbl_file` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT,
                            `uploader` int NOT NULL,
                            `file_path` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                            `original_filename` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
                            `create_at` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for tbl_project
-- ----------------------------
CREATE TABLE `tbl_project` (
                               `id` int unsigned NOT NULL AUTO_INCREMENT,
                               `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '项目名称',
                               `create_by` int NOT NULL COMMENT '创建者',
                               `create_at` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_at` timestamp DEFAULT NULL COMMENT '最后修改时间',
                               `update_by` int DEFAULT NULL COMMENT '最后修改者',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `iu_name_create_by` (`name`,`create_by`) COMMENT '创建者和项目名称唯一'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for tbl_project_base
-- ----------------------------
CREATE TABLE `tbl_project_base` (
                                    `id` int unsigned NOT NULL AUTO_INCREMENT,
                                    `project_id` int NOT NULL COMMENT '项目ID',
                                    `ask` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '问题',
                                    `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '回答',
                                    `sort` tinyint NOT NULL DEFAULT '0' COMMENT '排序',
                                    PRIMARY KEY (`id`),
                                    KEY `i_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for tbl_project_extend
-- ----------------------------
CREATE TABLE `tbl_project_extend` (
                                      `id` int unsigned NOT NULL AUTO_INCREMENT,
                                      `project_id` int NOT NULL COMMENT '项目ID',
                                      `keyword` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '关键字',
                                      `text` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文本内容',
                                      `file` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '文件内容(上传文件的ID以,分割)',
                                      `img` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图片内容(上传图片的ID以,分割)',
                                      PRIMARY KEY (`id`),
                                      KEY `i_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for tbl_project_user
-- ----------------------------
CREATE TABLE `tbl_project_user` (
                                    `id` int unsigned NOT NULL AUTO_INCREMENT,
                                    `project_id` int NOT NULL COMMENT '项目ID',
                                    `user_id` int NOT NULL COMMENT '用户ID',
                                    `role` tinyint NOT NULL COMMENT '角色',
                                    PRIMARY KEY (`id`),
                                    KEY `i_project_id_user_id` (`project_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
CREATE TABLE `tbl_user` (
                            `id` int unsigned NOT NULL AUTO_INCREMENT,
                            `mobile` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机',
                            `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
                            `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
                            `avatar` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
                            `role` tinyint NOT NULL COMMENT '角色',
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for tbl_category
-- ----------------------------
CREATE TABLE `tbl_category`  (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法规类别名',
                                 PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- ----------------------------
-- Table structure for tbl_passage
-- ----------------------------
CREATE TABLE `tbl_passage`  (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `regulation_id` int(11) NULL DEFAULT NULL COMMENT '法规id',
                                `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '段落内容',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT = '段落表';

-- ----------------------------
-- Table structure for tbl_regulation
-- ----------------------------
DROP TABLE IF EXISTS `tbl_regulation`;
CREATE TABLE `tbl_regulation`  (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
                                   `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件相对路径',
                                   `category_id` int(11) NULL DEFAULT NULL COMMENT '法规类别id',
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT = '法规表';

-- ----------------------------
-- Table structure for tbl_project_regulation
-- ----------------------------
CREATE TABLE `tbl_project_regulation` (
                                          `id` int unsigned NOT NULL AUTO_INCREMENT,
                                          `project_id` int NOT NULL COMMENT '项目ID',
                                          `regulation_id` int NOT NULL COMMENT '法规ID',
                                          PRIMARY KEY (`id`),
                                          KEY `i_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
