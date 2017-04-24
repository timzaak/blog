--评论
CREATE TABLE if not exists comments(
    id bigserial PRIMARY key,
    from_id BIGINT NOT NULL,
    to_id BIGINT NOT NULL,
    content varchar(256) NOT NULL,
    created_at timestamp NOT NULL DEFAULT current_timestamp
);

CREATE INDEX comments_to_id_index ON comments USING hash(to_id);

INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'1','2017-04-24 20:24:28.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'2','2017-04-24 20:24:29.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'3','2017-04-24 20:24:30.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'4','2017-04-24 20:24:31.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'5','2017-04-24 20:24:32.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'6','2017-04-24 20:24:33.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'8','2017-04-24 20:24:34.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'9','2017-04-24 20:24:35.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'10','2017-04-24 20:24:36.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'11','2017-04-24 20:24:37.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'12','2017-04-24 20:24:39.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'13','2017-04-24 20:24:40.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'14','2017-04-24 20:24:41.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'15','2017-04-24 20:24:42.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'16','2017-04-24 20:24:43.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'17','2017-04-24 20:24:44.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'18','2017-04-24 20:24:45.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'19','2017-04-24 20:24:46.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'20','2017-04-24 20:24:47.936235');
INSERT INTO comments(from_id,to_id,content,created_at) values(1,1,'21','2017-04-24 20:24:48.936235');