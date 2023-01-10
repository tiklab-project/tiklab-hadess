CREATE TABLE pack_library_maven(
        id VARCHAR(32) PRIMARY KEY,
        library_id VARCHAR(32) NOT NULL,
        group_id VARCHAR(12) NOT NULL,
        artifact_id VARCHAR(32),
        create_time 
);