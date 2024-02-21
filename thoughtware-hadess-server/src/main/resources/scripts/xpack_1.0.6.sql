-- ---------------------------
-- 扫描组
-- ----------------------------
create table pack_push_group(
    id            varchar(12) PRIMARY KEY,
    group_name   varchar(64) NOT NULL,
    repository_id varchar(12) NOT NULL,
    create_time  timestamp
);

ALTER TABLE pack_push_library ADD  push_group_id varchar(12);
ALTER TABLE pack_push_library ADD  library_version varchar(32);