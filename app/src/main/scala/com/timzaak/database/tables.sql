--评论
CREATE TABLE comments(
    id serial primary key, --
    time timestamp NOT NULL,-- 创建时间
    content varchar(256) NOT NULL,
    from_id integer NOT NULL,
    to_id integer NOT NULL
);

CREATE INDEX comments_to_id_index on comments USING hash(to_id);

--用户登录，注册
CREATE TABLE user_accounts(
    id serial primary key,
    acc varchar(32) NOT NULL,
    pwd varchar(64) NOT NULL
);



