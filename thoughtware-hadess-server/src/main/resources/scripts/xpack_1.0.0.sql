-- ---------------------------
-- 存储库表
-- ----------------------------
CREATE TABLE pack_storage(
    id VARCHAR(12) PRIMARY KEY,
    name varchar (32) NOT NULL,
    all_size varchar(12),
    residue_size varchar (12),
    create_time timestamp,
    update_time timestamp
);
-- ---------------------------
-- 制品库
-- ----------------------------
CREATE TABLE pack_repository(
     id VARCHAR(12) PRIMARY KEY,
     name varchar (128) NOT NULL,
     repository_type varchar(12) NOT NULL,
     repository_url varchar (328),
     type varchar (12) NOT NULL,
     rules varchar(12),
     storage_id varchar (12),
     description varchar(160),
     create_user varchar (12),
     category integer,
     color  integer,
     create_time timestamp,
     update_time timestamp
);

-- ---------------------------
-- 制品库maven 表
-- ----------------------------
CREATE TABLE pack_repository_maven(
       id VARCHAR(12) PRIMARY KEY,
       repository_id varchar (12) NOT NULL,
       version varchar(12),
       create_time timestamp
);
-- ---------------------------
-- 远程库代理表
-- ----------------------------
CREATE TABLE pack_repository_remote_proxy(
      id VARCHAR(12) PRIMARY KEY,
      repository_id varchar (12) NOT NULL,
      agency_url varchar (324) NOT NULL,
      agency_name varchar (32),
      user_name varchar(16),
      password varchar (16),
      create_time timestamp,
      update_time timestamp
);
-- ---------------------------
-- 组合库 关联制品库表
-- ----------------------------
CREATE TABLE pack_repository_group_items(
     id VARCHAR(12) PRIMARY KEY,
     repository_group_id varchar (12) NOT NULL,
     repository_id varchar (12) NOT NULL,
     create_time timestamp,
     update_time timestamp
);
-- ---------------------------
-- 复制信息表
-- ----------------------------
CREATE TABLE pack_repository_cluster_cfg(
    id VARCHAR(12) PRIMARY KEY,
    repository_id varchar (12) NOT NULL,
    source varchar (32),
    url varchar (32) NOT NULL,
    account varchar (18) NOT NULL,
    password varchar (18) NOT null,
    time_out varchar (18),
    create_user varchar (12),
    create_time timestamp,
    update_time timestamp
);

-- ---------------------------
-- 制品表
-- ----------------------------
CREATE TABLE pack_library(
    id VARCHAR(12) PRIMARY KEY,
    name varchar (128) NOT NULL,
    library_type varchar (32) NOT NULL,
    repository_id varchar (12) NOT NULL,
    new_version varchar (64),
    size     bigint,
    create_time timestamp,
    update_time timestamp
);
-- ---------------------------
-- 制品版本表
-- ----------------------------
CREATE TABLE pack_library_version(
     id VARCHAR(12) PRIMARY KEY,
     library_id varchar (12) NOT NULL,
     repository_id varchar(12) NOT NULL,
     library_type varchar (32) NOT NULL,
     version varchar(64) NOT NULL,
     hash varchar (64),
     pusher varchar(128),
     content_json TEXT,
     size  bigint,
     push_time timestamp,
     create_time timestamp,
     update_time timestamp
);
-- ---------------------------
-- 制品文件表
-- ----------------------------
CREATE TABLE pack_library_file(
      id VARCHAR(12) PRIMARY KEY,
      library_id varchar(12) NOT NULL,
      library_version_id varchar(12),
      repository_id varchar (12) NOT NULL,
      file_size varchar (32) NOT NULL,
      file_name varchar (128) NOT NULL,
      file_url varchar (688) NOT NULL,
      relative_path varchar (688),

      snapshot_version varchar(255),
      create_time  timestamp
);
-- ---------------------------
-- 制品maven 信息表
-- ----------------------------
CREATE TABLE pack_library_maven(
   id VARCHAR(12) PRIMARY KEY,
   library_id varchar(12) NOT NULL,
   group_id varchar (128) NOT NULL,
   artifact_id varchar (128) NOT NULL,
   repository_id varchar(12),
   create_time  timestamp
);
-- ---------------------------
-- 制品maven 信息表
-- ----------------------------
CREATE TABLE pack_pull_info(
   id VARCHAR(12) PRIMARY KEY,
   library_id varchar(12) NOT NULL,
   library_version_id varchar (12) NOT NULL,
   user_id varchar (12) NOT NULL,
   pull_create timestamp
);


-- ---------------------------
-- 推送中央仓库组
-- ----------------------------
create table pack_push_group(
     id      varchar(12) PRIMARY KEY,
     group_name   varchar(64) NOT NULL,
     repository_id varchar(12) NOT NULL,
     create_time  timestamp
);

-- ---------------------------
-- 推送制品列表
-- ----------------------------
CREATE TABLE pack_push_library(
  id VARCHAR(12) PRIMARY KEY,
  repository_id varchar(12) NOT NULL,
  library_id varchar (12) NOT NULL,
  last_push_time timestamp,
  last_push_result varchar(12),
  exec_state     varchar(12),
  push_group_id  varchar(12),
  library_version  varchar(64),
  user_id varchar (12)
);
