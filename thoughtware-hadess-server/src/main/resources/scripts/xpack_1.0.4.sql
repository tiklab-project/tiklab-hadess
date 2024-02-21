ALTER TABLE pack_library_version ADD  size bigint;

-- ---------------------------
-- 定时任务
-- ----------------------------
create table pack_time_task(
      id            varchar(12) PRIMARY KEY,
      scan_play_id       varchar (12),
      task_name          varchar(524),
      task_type     varchar(32),
      task_way      int,
      exec_state     int,
      create_time   timestamp,
      update_time  timestamp
);

-- ---------------------------
-- 定时任务 的实例
-- ----------------------------
create table pack_time_task_instance(
       id          varchar(12) PRIMARY KEY,
       time_task_id varchar (12),
       exec_object_id varchar(12),
       task_way      int,
       cron varchar(255) ,
       week_day int,
       exec_state     varchar(32),
       exec_time  varchar(12),
       create_time  timestamp
);