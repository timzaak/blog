--评论
create table comments (
    id serial primary key, --
    time timestamp NOT NULL,-- 创建时间
    content varchar(256) NOT NULL
);

--
CREATE TABLE users(
    id bigserial PRIMARY key
);

