--评论
CREATE TABLE if not exists comments(
    id bigserial PRIMARY key,
    from_id BIGINT NOT NULL,
    to_id BIGINT NOT NULL,
    content varchar(256) NOT NULL,
    created_at timestamp NOT NULL DEFAULT current_timestamp
);

CREATE INDEX comments_to_id_index ON comments USING hash(to_id);
