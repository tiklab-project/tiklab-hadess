ALTER TABLE pack_library_file ADD  size bigint;
ALTER TABLE pack_repository_maven ADD  cover_state int;

DROP TABLE pack_repository_remote_proxy;

-- ---------------------------
-- 远程库代理表
-- ----------------------------
CREATE TABLE pack_repository_remote_proxy(
     id VARCHAR(12) PRIMARY KEY,
     repository_id varchar (12) NOT NULL,
     remote_proxy_id varchar(12) NOT NULL,
     create_time timestamp

);


-- ---------------------------
-- 代理地址
-- ----------------------------
CREATE TABLE pack_remote_proxy(
     id VARCHAR(12) PRIMARY KEY,
     agency_url varchar (324) NOT NULL,
     agency_name varchar (32),
     agency_type varchar(12),
     type  int,
     account varchar(16),
     password varchar (16),
     create_time timestamp
);

INSERT INTO pack_remote_proxy (id, agency_name,agency_url ,agency_type,type,account,password,create_time) VALUES
('0230645439', 'maven-center','https://repo.maven.apache.org/maven2','maven',0,null,null,null),
('023066639','maven-aliyun','https://maven.aliyun.com/repository/public','maven',0,null,null,null),
('023077739','npmjs','https://registry.npmjs.org','npm',0,null,null,null),
('023088839','cnpm','https://registry.npmmirror.com','npm',0,null,null,null),
('023087839','docker-centre','https://registry-1.docker.io','docker',0,null,null,null);
