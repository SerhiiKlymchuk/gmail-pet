CREATE TABLE IF NOT EXISTS "message"
(
    id             serial       not null,
    ownerUserId    bigint       not null,
    receiverUserId bigint       not null,
    content        varchar(255) not null,
    subject        varchar(255) not null,
    reviewed         boolean      not null,
    date           timestamp    not null,

    primary key (id)
);