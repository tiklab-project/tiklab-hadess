
-- ---------------------------
-- 网络代理地址
-- ----------------------------
CREATE TABLE pack_repository_network_proxy(
     id VARCHAR(12) PRIMARY KEY,
     address varchar(128) NOT NULL,
     port   integer NOT NULL,
     description varchar(128),
     enable integer,
     create_time   timestamp
);
