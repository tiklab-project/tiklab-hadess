


ALTER TABLE pack_library_version ALTER COLUMN hash TYPE VARCHAR(248);

INSERT INTO pack_remote_proxy (id, agency_name,agency_url ,agency_type,type,account,password,create_time) VALUES
('0230655241', 'nuget.org','https://api.nuget.org/v3/index.json','nuget',0,null,null,null);
