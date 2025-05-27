
-- ---------------------------
-- 消息类型
-- ----------------------------
INSERT INTO pcs_mec_message_type (id, name, description, bgroup) VALUES ('SCAN_RESULT', '扫码结果', '制品库扫描结果', 'hadess');


-- ---------------------------
-- 发送消息途径
-- ----------------------------
INSERT INTO pcs_mec_message_notice (id, message_type_id, type, bgroup, message_send_type_id, scope, is_open) VALUES ('SCAN_RESULT', 'SCAN_RESULT', 2, 'hadess', 'site,email,qywechat', 2, 'true');


-- ---------------------------
--消息发送人
-- ----------------------------
INSERT INTO pcs_mec_message_notice_connect_user (id, message_notice_id, user_id) VALUES ('SCAN_RESULT', 'SCAN_RESULT', '111111');

-- 模版
INSERT INTO pcs_mec_message_template (id, msg_type_id, msg_send_type_id, title, content,link,bgroup,link_params) VALUES
('8363126abe9', 'SCAN_RESULT', 'site', '制品扫描', '制品扫描',null,'hadess',null),

('83f3e496abe9', 'SCAN_RESULT', 'email', '制品扫描', '${repositoryName}制品库中执行扫描${scanPlayName},存在漏洞${message}', NULL, 'hadess', NULL) ,

('SCAN_RESULT', 'SCAN_RESULT', 'qywechat', '制品扫描', '## 制品扫描\n
> 制品库：<font color=comment>${repositoryName}</font>\n
> 仓库组名称：<font color=info>[${holeMessage}](${qywxurl})</font>','/#/repository/${repositoryId}/scanDetails/${scanRecordId}','hadess',NULL);

