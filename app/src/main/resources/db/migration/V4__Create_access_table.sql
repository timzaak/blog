--用户权限表
create table if not exists access(
   id varchar(65) NOT NULL,   -- groupId+'s' 组权限，userId+'u' 用户权限
   resource text NOT NULL, --权限资源，可以是任何资源。？资源如何拓展？
   permission bigint default 0 --对该资源的具体操作
);

CREATE unique INDEX access_id_index ON access(id,resource);