-- 手机验证码信息存储， 或许需要考虑所有sms都存过来
CREATE TABLE if not exists sms(
    id bigserial PRIMARY key,
    mobile varchar(16) NOT NULL, --mobile number
    code varchar(6) NOT NULL,
    re_id varchar(20) NOT NULL, --reuslt_id
    created_at timestamp NOT NULL DEFAULT current_timestamp
);

CREATE INDEX sms_mobile_index ON sms(mobile);