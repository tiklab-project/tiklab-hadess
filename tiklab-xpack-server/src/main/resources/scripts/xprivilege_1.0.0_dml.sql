INSERT INTO pcs_prc_role VALUES ('1', '管理员角色', NULL, 'system', '1', 1, 1, 1);
INSERT INTO pcs_prc_role VALUES ('2', '普通角色', NULL, 'system', '1', 1, 0, 0);
INSERT INTO pcs_prc_role VALUES ('3', '项目管理员', NULL, 'system', '2', 1, 1, 1);
INSERT INTO pcs_prc_role VALUES ('4', '项目普通角色', NULL, 'system', '2', 1, 0, 0);


INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('permission','权限', 'xpack_permission',null ,1, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('messtype','消息通知类型', 'message_type',null ,1, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('messmanage','消息管理', 'message_setting',null ,1, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('version','版本许可证', 'xpack_version',null ,1, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('plugin','插件', 'xpack_plugin',null ,1, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('loginexce','操作日志', 'xpack_log',null ,1, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('projectall','项目设置', 'xpack_setting',null ,1, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('projectuser','项目成员', 'xpack_user',null ,6, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('projectdele','删除项目', 'xpack_delete','projectall' ,2, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('projectup','修改项目', 'xpack_update','projectall' ,3, 2);


INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('00e840ea5302', '1', 'permission');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('05d66918b2dd', '1', 'messtype');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('064d1a5ddbc5', '1', 'messmanage');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('0fd56de07eaf', '1', 'version');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('1134dbdbb6d0', '1', 'plugin');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('143e6010ba4b', '1', 'loginexce');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('256bca68cd16', '1', 'projectall');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('28b4ec49b63c', '1', 'projectuser');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('3572dd063f4f', '1', 'projectdele');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('39f06b018e83', '1', 'projectup');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('732aa5077352', '2', 'version');
INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('74217ab2e9eb', '2', 'loginexce');



INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('2fc38e2bd9ae', '1', 'hfg5371be8ec');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('35c69d9541ec', '1', '43e7d41decf7');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('9bdda9a0b509', '1', 'hf43e412151e');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('6f86c10ba475', '1', 'wqre9998fc00');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('6914e65f7c92', '1', '4235d2624bdf');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('d218d73a980e', '1', 'oug5371be8ec');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('15235be0755d', '1', '585d26bcbdf3');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('7a49af3c70ef', '1', '890e7d41decf');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('f3977c84615f', '1', '043e412151db');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('1ef93b2837c7', '1', '925371be8ec6');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('c1003fe47c16', '1', '447d9998fc00');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('6967ae37cbdb', '1', 'dd81bdbb52bc');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('cfeb2cb31110', '1', '57a3bcd1e5fe');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('72f160dcafa3', '1', '9633d9475886');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('4e8417f988c0', '1', '5fb7863b09a8');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('50cf5be6b67f', '1', '428be660dea3');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('594ba104d686', '1', '6b61fbe5091a');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('89ea961766fd', '1', '9c99b8a096c8');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('3078797e7cb5', '1', 'cb954a7c0be3');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('2f947cfb5ba8', '1', 'e8bf9843bc9d');
INSERT INTO "pcs_prc_role_function" ("id", "role_id", "function_id") VALUES ('74887911132b', '1', '325c2503007f');