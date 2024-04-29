-- ---------------------------
-- 系统功能
-- ----------------------------
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('b85f462e14d3', '扫描方案', 'system_scan', NULL, 28, '1');
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('826b1146ec74', '添加扫描方案', 'system_scan_add', 'b85f462e14d3', 29, '1');
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('8af76cdbd3fb', '删除扫描方案', 'system_sacn_delete', 'b85f462e14d3', 30, '1');
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('ec4db12cb516', '添加扫描方案漏洞', 'system_scan_hole_add', 'b85f462e14d3', 31, '1');
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('de52e243d49b', '代理地址', 'system_ proxy', NULL, 32, '1');
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('a2f7ddb52282', '添加代理地址', 'system_proxy_add', 'de52e243d49b', 33, '1');
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('241e0d14e9d3', '删除代理地址', 'system_proxy_delete', 'de52e243d49b', 34, '1');

INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('0d63f3d91019', '制品扫描', 'hadess_scan', NULL, 37, '2');
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('f49a8732b6e7', '访问代码扫描', 'hadess_scan_find', '0d63f3d91019', 38, '2');
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('8b28c8bd0cb0', '制品扫描管理', 'hadess_scan_manage', '0d63f3d91019', 1, '2');
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('b4355db522b8', 'maven仓库内上传制品', 'maven_upload', NULL, 36, '2');
INSERT INTO pcs_prc_function ("id", "name", "code", "parent_function_id", "sort", "type") VALUES ('32a5726a149a', '仓库策略', 'hadess_tactics', 'projectall', 39, '2');


-- ---------------------------
--超级管理员系统功能
-- ----------------------------
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('b5b171e41ba6', '111111', '9633d9475886');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('6076c761527f', '111111', 'dd81bdbb52bc');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('bec904dd750b', '111111', '57a3bcd1e5fe');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('e7b2aa44792b', '111111', '428be660dea3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('442be5416f5d', '111111', '5fb7863b09a8');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('9fa1f1546a68', '111111', 'e8bf9843bc9d');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c5d4a532e236', '111111', 'cb954a7c0be3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c948f55b3fb7', '111111', '9c99b8a096c8');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('cae8dbe849db', '111111', '325c2503007f');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c21ea0c7ec31', '111111', '6b61fbe5091a');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('833b06c2cf7b', '111111', '043e412151db');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('68aa68d252d7', '111111', '925371be8ec6');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('9040e27834c6', '111111', '447d9998fc00');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('a6283e0aa159', '111111', '890e7d41decf');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('f7c3b4e3ae9a', '111111', '585d26bcbdf3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('bf3c9a04bfb1', '111111', 'wqre9998fc00');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('b4aed57c81b5', '111111', '43e7d41decf7');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c6e7847ebd80', '111111', 'hfg5371be8ec');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('1da86c6d5295', '111111', 'oug5371be8ec');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('3339e1d867a2', '111111', 'hf43e412151e');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4cd0625b96b6', '111111', '4235d2624bdf');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('0c3de1ca5036', '111111', 'e5b34be19fab');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('7d2a7ed859a2', '111111', '4cc4e67319a0');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('d88bbcfed28d', '111111', 'cb6c8c3f4048');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('b3052f342cbb', '111111', '64bdf62686a4');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('5b380bd424d0', '111111', '8af76cdbd3fb');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('481e430355f2', '111111', '826b1146ec74');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('65f1fae80bac', '111111', 'ec4db12cb516');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4545666512c1', '111111', 'b85f462e14d3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('561fff3156b3', '111111', '241e0d14e9d3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('3e5719e36f29', '111111', 'a2f7ddb52282');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('13c8c483fddd', '111111', 'de52e243d49b');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('016b0ae30974', '111111', 'xpackbackup');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('ee72fa015fd6', '111111', 'plugin');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('fe4820a4ed0e', '111111', 'loginexce');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c60e42f06ce5', '111111', 'permission');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('ee2f7ff5a3c4', '111111', 'messmanage');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('05203b15519f', '111111', 'messtype');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4060c670f107', '111111', 'version');

-- ---------------------------
--管理员系统功能
-- ----------------------------
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('5868f1082ec9', '1', 'e5b34be19fab');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('756553ff5ce1', '1', '4cc4e67319a0');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('354fb026e3b1', '1', 'cb6c8c3f4048');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('5115ec2490fd', '1', '64bdf62686a4');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('2fb6de0c5718', '1', '8af76cdbd3fb');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('6e3c4049d10f', '1', '826b1146ec74');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('5ed03d6bcd82', '1', 'ec4db12cb516');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('501c37a63d25', '1', 'b85f462e14d3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('18ed908c1d2c', '1', '241e0d14e9d3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('cc849b301e9a', '1', 'a2f7ddb52282');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('dc6f1de943bf', '1', 'de52e243d49b');

-- ---------------------------
--项目超级管理员功能
-- ----------------------------
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('164555120d2a', 'pro_111111', 'projectup');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c5105f4ee0ca', 'pro_111111', 'projectdele');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('98bbdfa90cf5', 'pro_111111', 'projectall');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('9d7f738cdc1e', 'pro_111111', '8b28c8bd0cb0');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('238e3d636d3f', 'pro_111111', 'f49a8732b6e7');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('a1bc9bd611d6', 'pro_111111', '0d63f3d91019');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('31f93cf44bfd', 'pro_111111', 'b4355db522b8');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('fa4b9cb9352b', 'pro_111111', 'projectuser');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('8fdcf9704fe4', 'pro_111111', 'fd1e98f5d61c');

-- ---------------------------
--项目管理员功能
-- ----------------------------
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('22dd823553d0', '3', '8b28c8bd0cb0');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4bab5ecbd241', '3', 'f49a8732b6e7');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('f9554ab340fa', '3', '0d63f3d91019');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('83d593a16412', '3', 'b4355db522b8');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('8cb49d1ef09e', '3', 'projectuser');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('ddc35bff5dc9', '3', 'fd1e98f5d61c');

-- ---------------------------
--项目角色功能
-- ----------------------------
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('b20cc810de25', '4', '0d63f3d91019');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c64c8c8d89d7', '4', 'f49a8732b6e7');

-- ---------------------------
--消息发送人
-- ----------------------------
INSERT INTO pcs_mec_message_notice_connect_user ("id", "message_notice_id", "user_id") VALUES ('fbf8c6bb1d3c', 'HDS_CREATE', '111111');
INSERT INTO pcs_mec_message_notice_connect_user ("id", "message_notice_id", "user_id") VALUES ('defe335b21ef', 'HDS_UPDATE', '111111');
INSERT INTO pcs_mec_message_notice_connect_user ("id", "message_notice_id", "user_id") VALUES ('f11c3d87764b', 'HDS_DELETE', '111111');
