-- ---------------------------
-- 定时备份信息
-- ----------------------------
create table pack_backups(
      id          varchar(12) PRIMARY KEY,
      task_state  varchar (12) NOT NULL,
      log           text,
      exec_result varchar (12),
      exec_time   varchar(32),
      exec_state varchar (12)
);

INSERT INTO pack_backups (id, task_state,log,exec_result,exec_time,exec_state) VALUES ('0230645439', 'false',null,'non','non','end');





























































