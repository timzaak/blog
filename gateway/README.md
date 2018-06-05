### OpenResty[Deprecated]
Deprecated的原因主要是
1. consul 等动态配置直接使用集群管理工具就可以替代了
2. 一些配置随着版本变化太大，到时候，还是要现查配置，另外一些配置问题，比较诡异。需要深入理解 Nginx 后，才能解决。目前的配置没啥价值

要记录常见OpenResty配置，以及一些扩展。使用OpenResty官方的opm管理代码

### 初始化脚本

由于opm还不太成熟，所以依赖还是先走shell脚本来初始化

```shell
opm --cwd detailyang/lua-resty-cors
```


### docker网络瓶颈

http://wiki.jikexueyuan.com/project/openresty/web/docker.html

### docker镜像跑起来

```shell
docker run -d --net=host  -v ${PWD}:/usr/local/openresty/nginx  openresty/openresty:alpine  openresty
```

### TODO

- [X] 负载均衡
- [X] consul配置监听（方便实现动态可用服务ip变化）
- [X] WebSocket 配置
- [X] 跨域配置(detailyang/lua-resty-cors)
- [X] Https配置

