### OpenResty

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

