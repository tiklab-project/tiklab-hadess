-- ---------------------------
-- 扫描制品记录
-- ----------------------------
create table pack_scan_library(
      id          varchar(12) PRIMARY KEY,
      library_id varchar (12),
      library_version_id varchar(12),
      repository_id varchar(12),
      scan_play_id  varchar(12),
      create_time  timestamp
);

-- ---------------------------
-- 扫描结果记录
-- ----------------------------
create table pack_scan_record(
     id          varchar(12) PRIMARY KEY,
     scan_library_id varchar(12),
     library_id varchar(12),
     library_version varchar(64),
     hole_severity integer,
     hole_high   integer,
     hole_middle integer,
     hole_low    integer,
     scan_time_long varchar(32),
     scan_play_id   varchar(12),
     scan_group     varchar(12),
     scan_user_id   varchar(12),
     record_type varchar(12),
     scan_way varchar(12),
     scan_result varchar(12),
     log text,
     create_time  timestamp
);


-- ---------------------------
-- 扫描设置
-- ----------------------------
create table pack_scan_set(
      id          varchar(12) PRIMARY KEY,
      address varchar (64),
      token  varchar (128)
);
-- ---------------------------
-- 扫描制品的依赖
-- ----------------------------
create table pack_scan_rely(
       id          varchar(12) PRIMARY KEY,
       scan_library_id varchar(12) NOT NULL,
       scan_record_id varchar(12) NOT NULL,
       rely_name  varchar(64) NOT NULL,
       rely_vendor varchar(64),
       rely_version varchar(64),
       rely_language varchar(32),
       rely_path   varchar(428),
       rely_type  varchar(32),
       rely_parent_id varchar(12),
       rely_one_id varchar(12),
       hole_count integer,
       rely_licenses varchar(64),
       general_record_id varchar(12),
       create_time timestamp
);

-- ---------------------------
-- 扫描漏洞结果
-- ----------------------------
create table pack_scan_result(
     id          varchar(12) PRIMARY KEY,
     library_id varchar(12) NOT NULL,
     scan_library_id varchar(12),
     scan_record_id varchar(12) NOT NULL,
     hole_type varchar(12),
     hole_name varchar(324),
     hole_level  integer,
     hole_cwe varchar(64),
     hole_cve varchar(64),
     hole_cnnvd varchar(64),
     hole_cnvd varchar(64),
     hole_xmirror varchar(64),
     release_time varchar(32),
     hole_desc   text,
     repair_suggest text,
     creat_time timestamp

);

-- ---------------------------
-- 扫描制品计划
-- ----------------------------
create table pack_scan_play(
       id          varchar(12) PRIMARY KEY,
       play_name varchar (324) NOT NULL,
       repository_id varchar(12) NOT NULL,
       scan_time timestamp,
       scan_scheme_id varchar(12),
       create_time  timestamp
);

-- ---------------------------
-- 扫描方案
-- ----------------------------
create table pack_scan_scheme(
     id           varchar(12) PRIMARY KEY,
     scheme_name  varchar (128) NOT NULL,
     language     varchar(12),
     describe     varchar (246),
     scheme_type     varchar (12),
     create_time  timestamp
);

-- ---------------------------
-- 扫描方案漏洞关系表
-- ----------------------------
create table pack_scan_scheme_hole(
      id           varchar(12) PRIMARY KEY,
      scan_scheme_id  varchar (12) NOT NULL,
      scan_hole_id     varchar(12),
      create_time  timestamp
);

-- ---------------------------
-- 扫描漏洞
-- ----------------------------
create table pack_scan_hole(
       id          varchar(12) PRIMARY KEY,
       vendor  varchar (64) NOT NULL,
       product varchar(32),
       version varchar(528),
       language    varchar(12),
       hole_name varchar(528),
       hole_number  varchar(528),
       hole_level    integer,
       create_time  timestamp,
       suggestion     text,
       describe       text
);

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

INSERT INTO pack_scan_set (id, address, token) VALUES ('0230645439', 'https://opensca.xmirror.cn','08af1529-8095-4901-8727-d674971ded79');




























































