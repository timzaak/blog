--用户分组
create table if not exists user_groups(
    user_id bigint,
    group_id int,
    updated_at timestamp NOT NULL DEFAULT current_timestamp
);

create index user_groups_user_id_index on user_groups(user_id);
