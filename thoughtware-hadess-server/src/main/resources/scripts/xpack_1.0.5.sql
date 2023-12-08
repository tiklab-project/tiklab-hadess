-- ---------------------------
-- 扫描制品计划
-- ----------------------------
create table pack_scan_play(
      id          varchar(12) PRIMARY KEY,
      play_name varchar (324) NOT NULL,
      repository_id varchar(12) NOT NULL,
      scan_time timestamp,
      create_time  timestamp
);

ALTER TABLE pack_scan_library ADD scan_play_id varchar(12);
ALTER TABLE pack_scan_record ADD scan_play_id varchar(12);
ALTER TABLE pack_scan_record ADD scan_group varchar(12);