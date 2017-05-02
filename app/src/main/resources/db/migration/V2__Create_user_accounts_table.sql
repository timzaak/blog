--用户登录，注册
CREATE TABLE if not exists user_accounts(
    id bigserial PRIMARY key,
    acc varchar(32) NOT NULL,
    pwd varchar(64) NOT NULL,
    created_at timestamp NOT NULL DEFAULT current_timestamp
);

insert into user_accounts(acc,pwd) values('system','')