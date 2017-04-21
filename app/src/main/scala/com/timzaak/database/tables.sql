--评论
CREATE TABLE comments(
    id bigserial PRIMARY key,
    from_id BIGINT NOT NULL,
    to_id BIGINT NOT NULL,
    content varchar(256) NOT NULL,
    time TIMESTAMP NOT NULL-- 创建时间
);

CREATE INDEX comments_to_id_index ON comments USING hash(to_id);

--用户登录，注册
CREATE TABLE user_accounts(
    id bigserial PRIMARY key,
    acc varchar(32) NOT NULL,
    pwd varchar(64) NOT NULL
);