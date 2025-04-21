


-- ---------------------------
-- composer制品扩展数据
-- ----------------------------
CREATE TABLE pack_library_composer(
      id VARCHAR(12) PRIMARY KEY,
      library_id varchar(12) NOT NULL,
      metadata_path varchar(448) NOT NULL
);



INSERT INTO pack_remote_proxy (id, agency_name,agency_url ,agency_type,type,account,password,create_time) VALUES
                                                                                                              ('0230641241', 'Aliyun Composer','https://mirrors.aliyun.com/composer/','composer',0,null,null,null),
                                                                                                              ('0230641242', 'Composer','https://packagist.org/','composer',0,null,null,null);
