

-- ---------------------------
-- pypi制品扩展数据
-- ----------------------------
CREATE TABLE pack_library_pypi(
          id VARCHAR(12) PRIMARY KEY,
          library_id varchar(12) NOT NULL,
          metadata_path varchar(248) NOT NULL
);


ALTER TABLE pack_library DROP COLUMN size;

ALTER TABLE pack_library_version MODIFY version VARCHAR(248);

INSERT INTO pack_remote_proxy (id, agency_name,agency_url ,agency_type,type,account,password,create_time) VALUES
                                                                                                              ('0230641234', 'TencentCloud PyPI','https://mirrors.cloud.tencent.com/pypi/simple/','pypi',0,null,null,null),
                                                                                                              ('0230641235', 'AliYun PyPI','https://mirrors.aliyun.com/pypi/simple/','pypi',0,null,null,null),
                                                                                                              ('0230641236', 'PyPI','https://pypi.org/simple/','pypi',0,null,null,null);