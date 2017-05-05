--用户分组
create table if not exists groups(
    id serial PRIMARY KEY,
    parent_id int default 0,
    group_name varchar(30) NOT NULL,
    description varchar(60),
    updated_at timestamp NOT NULL DEFAULT current_timestamp
);

create index groups_child_id_index on groups(parent_id);
