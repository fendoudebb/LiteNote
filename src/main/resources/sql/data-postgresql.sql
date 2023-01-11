insert into sys_user(id, username, password, create_by)
select
    1, 'admin', '{noop}admin', 'System'
where not exists (
    select 1 from sys_user where username = 'admin'
);
