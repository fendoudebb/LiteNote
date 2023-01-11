create table if not exists sys_user
(
    id                  bigint  not null constraint pk_sys_user_id primary key,
    username            text    not null,
    password            text    not null,
    account_expired     boolean default false,
    account_locked      boolean default false,
    credentials_expired boolean default false,
    disabled            boolean default false,
    create_by           text    not null ,
    create_date         timestamp with time zone default CURRENT_TIMESTAMP,
    update_by           text,
    update_date         timestamp with time zone
);

create unique index if not exists index_sys_user_username on sys_user (username);
