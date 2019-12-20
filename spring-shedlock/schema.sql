SET default_storage_engine=INNODB;

CREATE TABLE shedlock(
   name VARCHAR(64),
   lock_until TIMESTAMP(3) NULL,
   locked_at TIMESTAMP(3) NULL,
   locked_by  VARCHAR(255),
   PRIMARY KEY (name)
);