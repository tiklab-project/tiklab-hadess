INSERT INTO pcs_prc_function (id,name,code,parent_function_id,sort,type) VALUES ('xpackbackup','备份与恢复', 'xpack_backup',null ,1, 1);

INSERT INTO pcs_prc_role_function (id, role_id, function_id) VALUES ('00e50ea15102', '1', 'xpackbackup');
