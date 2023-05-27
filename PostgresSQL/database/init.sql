CREATE TABLE users (
    id varchar(100) PRIMARY KEY NOT NULL,
    username varchar(100)  NOT NULL,
    password varchar(100) NOT NUll
);

CREATE TABLE videos (
    id varchar(100) PRIMARY KEY NOT NULL,
    name character varying(255) NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    size BIGSERIAL NOT NULL,
    data bytea NOT NULL
);

alter table users
    add access_token varchar(100);
