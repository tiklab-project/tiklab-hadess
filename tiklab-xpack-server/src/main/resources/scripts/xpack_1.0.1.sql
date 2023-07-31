-- ---------------------------
-- 推送制品列表
-- ----------------------------
CREATE TABLE pack_push_library(
     id VARCHAR(12) PRIMARY KEY,
     repository_id varchar(12) NOT NULL,
     library_id varchar (12) NOT NULL,
     last_push_time timestamp,
     last_push_result varchar(12),
     user_id varchar (12)
);


ALTER TABLE pack_repository_maven RENAME COLUMN verify to version;
