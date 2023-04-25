-- ---------------------------
-- 存储库表
-- @dsm.cmd.id="1001"
-- ----------------------------
CREATE TABLE pack_storage(
    id VARCHAR(32) PRIMARY KEY,
    name varchar (32) NOT NULL,
    all_size varchar(12),
    residue_size varchar (12),
    create_time timestamp,
    update_time timestamp
);
-- ---------------------------
-- 制品库
-- @dsm.cmd.id="1002"
-- ----------------------------
CREATE TABLE pack_repository(
     id VARCHAR(32) PRIMARY KEY,
     name varchar (128) NOT NULL,
     repository_type varchar(12) NOT NULL,
     repository_url varchar (328),
     type varchar (12) NOT NULL,
     storage_id varchar (32),
     description varchar(160),
     create_user varchar (32) NOT NULL,
     create_time timestamp,
     update_time timestamp
);

-- ---------------------------
-- 制品库maven 表
-- @dsm.cmd.id="1003"
-- ----------------------------
CREATE TABLE pack_repository_maven(
       id VARCHAR(32) PRIMARY KEY,
       repository_id varchar (32) NOT NULL,
       verify varchar(12),
       create_time timestamp
);
-- ---------------------------
-- 远程库代理表
-- @dsm.cmd.id="1004"
-- ----------------------------
CREATE TABLE pack_repository_remote_proxy(
      id VARCHAR(32) PRIMARY KEY,
      repository_id varchar (32) NOT NULL,
      agency_url varchar (324) NOT NULL,
      agency_name varchar (32),
      user_name varchar(16),
      password varchar (16),
      create_time timestamp,
      update_time timestamp
);
-- ---------------------------
-- 组合库 关联制品库表
-- @dsm.cmd.id="1005"
-- ----------------------------
CREATE TABLE pack_repository_group_items(
     id VARCHAR(32) PRIMARY KEY,
     repository_group_id varchar (32) NOT NULL,
     repository_id varchar (32) NOT NULL,
     create_time timestamp,
     update_time timestamp
);
-- ---------------------------
-- 复制信息表
-- @dsm.cmd.id="1006"
-- ----------------------------
CREATE TABLE pack_repository_cluster_cfg(
    id VARCHAR(32) PRIMARY KEY,
    repository_id varchar (32) NOT NULL,
    source varchar (32),
    url varchar (32) NOT NULL,
    account varchar (18) NOT NULL,
    password varchar (18) NOT null,
    time_out varchar (18),
    create_user varchar (32),
    create_time timestamp,
    update_time timestamp
);

-- ---------------------------
-- 制品表
-- @dsm.cmd.id="1007"
-- ----------------------------
CREATE TABLE pack_library(
    id VARCHAR(32) PRIMARY KEY,
    name varchar (128) NOT NULL,
    library_type varchar (32) NOT NULL,
    repository_id varchar (32) NOT NULL,
    new_version varchar (64),
    create_time timestamp,
    update_time timestamp
);
-- ---------------------------
-- 制品版本表
-- @dsm.cmd.id="1008"
-- ----------------------------
CREATE TABLE pack_library_version(
     id VARCHAR(32) PRIMARY KEY,
     library_id varchar (32) NOT NULL,
     repository_id varchar(32) NOT NULL,
     library_type varchar (32) NOT NULL,
     version varchar(64) NOT NULL,
     hash varchar (64),
     pusher varchar(128),
     content_json longtext,
     push_time timestamp,
     create_time timestamp,
     update_time timestamp
);
-- ---------------------------
-- 制品文件表
-- @dsm.cmd.id="1009"
-- ----------------------------
CREATE TABLE pack_library_file(
      id VARCHAR(32) PRIMARY KEY,
      library_id varchar(32) NOT NULL,
      library_version_id varchar(32) NOT NULL,
      repository_id varchar (32) NOT NULL,
      file_size varchar (32) NOT NULL,
      file_name varchar (64) NOT NULL,
      file_url varchar (248) NOT NULL,
      relative_path varchar (246),
      create_time  timestamp
);
-- ---------------------------
-- 制品maven 信息表
-- @dsm.cmd.id="1010"
-- ----------------------------
CREATE TABLE pack_library_maven(
   id VARCHAR(32) PRIMARY KEY,
   library_id varchar(32) NOT NULL,
   group_id varchar (128) NOT NULL,
   artifact_id varchar (128) NOT NULL,
   create_time  timestamp
);
-- ---------------------------
-- 制品maven 信息表
-- @dsm.cmd.id="1011"
-- ----------------------------
CREATE TABLE pack_pull_info(
   id VARCHAR(32) PRIMARY KEY,
   library_id varchar(32) NOT NULL,
   library_version_id varchar (32) NOT NULL,
   user_id varchar (32) NOT NULL,
   pull_create timestamp
);
