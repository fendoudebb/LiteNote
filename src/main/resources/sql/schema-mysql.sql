create table if not exists sys_user
(
    id                  bigint unsigned not null primary key,
    username            varchar(16)     not null,
    password            varchar(255)    not null,
    account_expired     boolean default false,
    account_locked      boolean default false,
    credentials_expired boolean default false,
    disabled            boolean default false,
    create_by           varchar(16)    not null ,
    create_date         datetime default CURRENT_TIMESTAMP,
    update_by           varchar(16),
    update_date         datetime,
    constraint idx_uq_sys_user_username unique (username)
);
