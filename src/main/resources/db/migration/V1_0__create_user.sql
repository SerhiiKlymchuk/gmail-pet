CREATE TABLE IF NOT EXISTS "user"
(
    id         serial       not null,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    username   varchar(255) not null,
    password   varchar(255) not null,
    role       varchar(255) not null,
    active     boolean      not null,

    primary key (id),
    unique(username)
);