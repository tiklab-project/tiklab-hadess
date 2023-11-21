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
      version varchar(64),
      language    varchar(12),
      hole_name varchar(528),
      hole_number  varchar(64),
      hole_level    integer,
      create_time  timestamp,
      suggestion     text,
      describe       text
);
ALTER TABLE pack_scan_play ADD scan_scheme_id varchar(12);