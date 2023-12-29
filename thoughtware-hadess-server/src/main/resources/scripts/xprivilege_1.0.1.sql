-- ---------------------------
-- 日志类型
-- ----------------------------
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('HDS_CREATE', '创建制品库', 'hadess');
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('HDS_UPDATE', '更新制品库', 'hadess');
INSERT INTO pcs_op_log_type (id, name, bgroup) VALUES ('HDS_DELETE', '删除制品库', 'hadess');



-- ---------------------------
-- 消息类型
-- ----------------------------
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('HDS_CREATE', '创建制品库', '制品库创建消息', 'hadess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('HDS_UPDATE', '更新制品库', '制品库更新消息', 'hadess');
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('HDS_DELETE', '删除制品库', '制品库删除消息', 'hadess');


-- ---------------------------
-- 发送消息途径
-- ----------------------------
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('HDS_CREATE', 'HDS_CREATE', 1, 'hadess', 'site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('HDS_UPDATE', 'HDS_UPDATE', 1, 'hadess', 'site');
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id) VALUES ('HDS_DELETE', 'HDS_DELETE', 1, 'hadess', 'site');



-- ---------------------------
-- 发送消息模版
-- ----------------------------
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content,link,bgroup,link_params) VALUES ('HDS_CREATE', 'HDS_CREATE', 'site', '创建制品库', '创建制品库',null,'hadess',null);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content,link,bgroup,link_params) VALUES ('HDS_UPDATE', 'HDS_UPDATE', 'site', '更新制品库', '更新制品库',null,'hadess',null);
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content,link,bgroup,link_params) VALUES ('HDS_DELETE', 'HDS_DELETE', 'site', '删除制品库', '删除制品库',null,'hadess',null);

