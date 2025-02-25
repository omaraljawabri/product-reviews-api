CREATE TABLE IF NOT EXISTS tb_user(
    id BIGSERIAL PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    last_name varchar(255),
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);