--评论
CREATE TABLE comments(
    id bigserial primary key, --
    time timestamp NOT NULL,-- 创建时间
    content varchar(256) NOT NULL
);

--用户登录，注册
CREATE TABLE user_accounts(
    id bigserial primary key,
    acc varchar(32) NOT NULL,
    pwd varchar(64) NOT NULL
);

