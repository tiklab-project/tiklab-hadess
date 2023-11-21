-- ---------------------------
-- 扫描制品记录
-- ----------------------------
create table pack_scan_library(
      id          varchar(12) PRIMARY KEY,
      library_id varchar (12),
      library_version_id varchar(12),
      repository_id varchar(12),
      create_time  timestamp
);

-- ---------------------------
-- 扫描结果记录
-- ----------------------------
create table pack_scan_record(
      id          varchar(12) PRIMARY KEY,
      scan_library_id varchar(12) NOT NULL,
      library_id varchar(12) NOT NULL,
      library_version varchar(64),
      hole_severity integer,
      hole_high   integer,
      hole_middle integer,
      hole_low    integer,
      scan_time_long varchar(32),
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

INSERT INTO pack_scan_set (id, address, token) VALUES ('0230645439', 'https://opensca.xmirror.cn','08af1529-8095-4901-8727-d674971ded79');




























































