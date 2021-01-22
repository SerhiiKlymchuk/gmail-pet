CREATE TABLE IF NOT EXISTS "message"
(
    id               serial       not null,
    sender_user_id   bigint       not null,
    receiver_user_id bigint       not null,
    content          text         not null,
    subject          varchar(255) not null,
    reviewed         boolean      not null,
    date             timestamp    not null,

    primary key (id)
);