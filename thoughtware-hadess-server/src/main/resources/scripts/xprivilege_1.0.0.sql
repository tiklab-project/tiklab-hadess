INSERT INTO pcs_prc_role VALUES ('1', '管理员角色', NULL, 'system', '1', 1, 0, 1);
INSERT INTO pcs_prc_role VALUES ('2', '普通角色', NULL, 'system', '1', 0, 1, 1);
INSERT INTO pcs_prc_role VALUES ('3', '项目管理员', NULL, 'system', '2', 1, 0, 1);
INSERT INTO pcs_prc_role VALUES ('4', '项目成员', NULL, 'system', '2', 0, 1, 1);


-- ---------------------------
-- 权限功能
-- ----------------------------
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('b70aeb4ccd60','消息', 'news',null ,1, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('b409dd8f8575','消息发送方式', 'news_send_way','b70aeb4ccd60' ,30, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('8d39e613ca39','消息通知方案', 'news_inform_scheme','b70aeb4ccd60' ,1, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('484a5c1b9417','扫描方案', 'hadess_scan',null ,32, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('a2fa3ef2413b','添加扫描方案', 'hadess_scan_add','484a5c1b9417' ,33, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('5d92f1c23c17','删除扫描方案', 'hadess_scan_delete','484a5c1b9417' ,34, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('69ef12e9dfd0','添加扫描方案漏洞', 'hadess_scan_hole_add','484a5c1b9417' ,54, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('8feb1786149e','备份与恢复', 'hadess_backup',null ,36, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('38ba18169587','权限', 'hadess_authority',null ,37, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('43622a8ad389','资源监控', 'hadess_resource',null ,38, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('f7249f3f0b63','操作日志', 'hadess_log',null ,35, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('7ee226136c94','代理地址', 'hadess_proxy',null ,50, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('9be10fa48e38','添加代理地址', 'hadess_proxy_add','7ee226136c94' ,51, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('6575d360e072','更新代理地址', 'hadess_proxy_update','7ee226136c94' ,53, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('bcdf98dcf501','删除代理地址', 'hadess_proxy_delete','7ee226136c94' ,52, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('6c73e4bd0d4c','应用访问权限', 'access_auth',null ,1, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('e67450b47a1d','删除扫描方案漏洞', 'hadess_scan_hole_delete','484a5c1b9417' ,55, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('301002068f78','添加制品库', 'hadess_rpy_add',null ,40, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('5806f877751e','操作指引', 'hadess_operate_guide',null ,57, 1);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('84440b3f89c9','制品删除', 'hadess_library_delete',null ,58, 1);

INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('4e560bf41209','制品库设置', 'rpy_setting',null ,39, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('e2647d27302c','更新制品库', 'rpy_update','4e560bf41209' ,41, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('d7b517e67879','删除制品库', 'rpy_delete','4e560bf41209' ,42, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('2d9a47355b6e','制品库权限', 'rpy_authority',null ,44, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('2b87261ca9da','制品库策略', 'rpy_tactics',null ,1, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('dd03b9487749','制品库成员', 'rpy_user',null ,45, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('4f2543a2a7c8','制品', 'library',null ,47, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('bbe25463ce0a','删除制品', 'library_delete','4f2543a2a7c8' ,48, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('0890b8644d64','仓库内上传制品', 'library_rpy_upload','4f2543a2a7c8' ,49, 2);
INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('10941027da0c','制品扫描管理', 'rpy_scan_manage',null ,1, 2);


-- ---------------------------
-- hadess超级管理员权限
-- ----------------------------
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('d23792cc9c8c', '111111', '9633d9475886');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('384b18a7f9c2', '111111', 'dd81bdbb52bc');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('63f60242533e', '111111', '57a3bcd1e5fe');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('829698a10622', '111111', '428be660dea3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('a323f825b0aa', '111111', '5fb7863b09a8');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('dd857b52ce24', '111111', 'e8bf9843bc9d');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('b19c7416f924', '111111', 'cb954a7c0be3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('efce545b0055', '111111', '9c99b8a096c8');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('cea00ad7bc02', '111111', '325c2503007f');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('df418b8998be', '111111', '6b61fbe5091a');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('66dd89b312e9', '111111', '043e412151db');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('76eaf1126b82', '111111', '925371be8ec6');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('21baf6d62bea', '111111', '447d9998fc00');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('b38585489e87', '111111', '890e7d41decf');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('48fa5d65a563', '111111', '585d26bcbdf3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4ada0eec808f', '111111', 'wqre9998fc00');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('5855d77bf5fc', '111111', '43e7d41decf7');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('6ef5c89c2212', '111111', 'hfg5371be8ec');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('187ccbb73b08', '111111', 'oug5371be8ec');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c1f2a4344086', '111111', 'hf43e412151e');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('ce65e7d623dc', '111111', '4235d2624bdf');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('cc6eab8e0c65', '111111', 'e5b34be19fab');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('147b68aae016', '111111', '4cc4e67319a0');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('abb95a981a0a', '111111', 'cb6c8c3f4048');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('33b02b4fc465', '111111', '64bdf62686a4');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('24f491e3e525', '111111', 'b409dd8f8575');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('925dd853420b', '111111', '8d39e613ca39');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('94949177597f', '111111', 'b70aeb4ccd60');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('79e8845648cc', '111111', '5d92f1c23c17');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('3243b5800a8e', '111111', 'a2fa3ef2413b');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('86f1ca03cea5', '111111', '484a5c1b9417');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('b5bc59bb0c33', '111111', '8feb1786149e');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('689d4479f8cb', '111111', 'f7249f3f0b63');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('ca027a22532a', '111111', '38ba18169587');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('ce35f0f82cdb', '111111', '43622a8ad389');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('25a6a867471a', '111111', 'bcdf98dcf501');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('99b65cdae4c8', '111111', '9be10fa48e38');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('33adc9c8a683', '111111', '7ee226136c94');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('56adc1561683', '111111', '6c73e4bd0d4c');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('dea767404643', '111111', '6575d360e072');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4280eec78021', '111111', '69ef12e9dfd0');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('e6450b34001d', '111111', 'e67450b47a1d');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('7e3deb429cee', '111111', '301002068f78');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('51501f17751e', '111111', '5806f877751e');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('8441489189c9', '111111', '84440b3f89c9');


-- ---------------------------
-- hadess管理员权限
-- ----------------------------
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('57acfb04f576', '1', '9633d9475886');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('90bd538e07c4', '1', 'dd81bdbb52bc');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('123630ba3e6e', '1', '57a3bcd1e5fe');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('045e7a56c49f', '1', '428be660dea3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('254947c7224f', '1', '5fb7863b09a8');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('965964f8bdd9', '1', 'e8bf9843bc9d');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('8ac8e3f91385', '1', 'cb954a7c0be3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('01bfcd3a2f88', '1', '9c99b8a096c8');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('89de14ab542b', '1', '325c2503007f');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4c9b2aed6aac', '1', '6b61fbe5091a');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('069d7d3dc2d3', '1', '043e412151db');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('403749c91c3e', '1', '925371be8ec6');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('abd6e8e2aec6', '1', '447d9998fc00');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('7b8f7ed75a62', '1', '890e7d41decf');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('418c6359535c', '1', '585d26bcbdf3');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('033af548f3e9', '1', 'wqre9998fc00');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('24f1e91c648b', '1', '43e7d41decf7');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('173d9052323a', '1', 'hfg5371be8ec');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('d21fc7aad0dd', '1', 'oug5371be8ec');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('fa2ccd03200e', '1', 'hf43e412151e');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('6a6a1f8dca39', '1', '4235d2624bdf');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('99c9ee1ccc4b', '1', 'e5b34be19fab');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('2a3dd83152aa', '1', '4cc4e67319a0');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('36e96a99b630', '1', 'cb6c8c3f4048');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('eca27d5de3a3', '1', '64bdf62686a4');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4dd55ca6c81f', '1', 'b409dd8f8575');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('fcc1e7e0e867', '1', '8d39e613ca39');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('8603483cc896', '1', 'b70aeb4ccd60');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('e8f954854c1d', '1', '5d92f1c23c17');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('6306812b5d37', '1', 'a2fa3ef2413b');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('9cb37d93f0a6', '1', '484a5c1b9417');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('5bb2b8268d93', '1', '8feb1786149e');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('07d06ee4d1c9', '1', 'f7249f3f0b63');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('ded7bac981f8', '1', '38ba18169587');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('123012d0f38e', '1', '43622a8ad389');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('ad58b27b6148', '1', 'bcdf98dcf501');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('0646bb3441ac', '1', '9be10fa48e38');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c3d2fe61b75f', '1', '7ee226136c94');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('811cscs51104', '1', '6c73e4bd0d4c');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('654cb5ff8da2', '1', '6575d360e072');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('6e30e535b9fa', '1', '69ef12e9dfd0');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('e16410b47a1d', '1', 'e67450b47a1d');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('6415723657e0', '1', '301002068f78');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('161411703541', '1', '5806f877751e');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('520das4f89c9', '1', '84440b3f89c9');

-- ---------------------------
-- hadess普通角色权限
-- ----------------------------
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c2975abfec86', '2', 'b409dd8f8575');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('6526622645c6', '2', '8d39e613ca39');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4c994dd988e9', '2', 'b70aeb4ccd60');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('34fbb100ca01', '2', 'f7249f3f0b63');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('99968e743b91', '2', '43622a8ad389');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('10das0243225', '2', '5806f877751e');


-- ---------------------------
-- 制品库超级管理员权限
-- ----------------------------
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('c8155dd35d03', 'pro_111111', 'd7b517e67879');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('e59194e6332f', 'pro_111111', '2b87261ca9da');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('06608b3e86bb', 'pro_111111', 'e2647d27302c');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('064a86a8ca90', 'pro_111111', '4e560bf41209');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('393a7863bd05', 'pro_111111', '0890b8644d64');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('566ec61bfde7', 'pro_111111', 'bbe25463ce0a');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('333e43ced853', 'pro_111111', '4f2543a2a7c8');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('50f006ff6850', 'pro_111111', 'dd03b9487749');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('943c284cbe5c', 'pro_111111', '2d9a47355b6e');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('350f30c864d9', 'pro_111111', '10941027da0c');


-- ---------------------------
-- 制品库管理员权限
-- ----------------------------
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4a4a8f128bbf', '3', 'd7b517e67879');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4bbeddbb8a2b', '3', '2b87261ca9da');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('cb30a826dec7', '3', 'e2647d27302c');

INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('4d361f7ffdf6', '3', '4e560bf41209');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('a136f687899e', '3', '0890b8644d64');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('2d1501bfe6b5', '3', 'bbe25463ce0a');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('fbd0d7a943b0', '3', '4f2543a2a7c8');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('2fb847cab609', '3', 'dd03b9487749');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('43959ef5d937', '3', '2d9a47355b6e');
INSERT INTO pcs_prc_role_function ("id", "role_id", "function_id") VALUES ('006a213fec2c', '3', '10941027da0c');


