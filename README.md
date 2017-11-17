### Server Starter
目前用到的技术选型，如下
> 数据交互格式： Graphql

> 框架：akka系列, sangrid

> 语言：scala2.12

> 数据库： postgrel

> 库： slick, json4s, flyway

> 容器: docker

> 部署工具: python3 Fabric3

### 代码组织方式
> app 目录下是主要的 bootstrap 、业务逻辑和未成熟的组件，基于 git submodule 来做组件分离代码管理，凡是成熟的组件尽量脱离 app 目录。
> docker 目录存放一些 docker 配置，以及开发依赖的环境。
> script 目录存放一些服务学习脚本。目前是 js 代码。
> benchmark 目录存放性能测试代码。
### 开始
```sh
#init docker
cd docker
docker-compose up

#init database
cd ../app
sbt flywayMigrate

```

### 生产环境日志
logback.pro.xml

命令： `sbt -Dlogback.configurationFile=logback.pro.xml run `

### 生产环境部署脚本
```sh
#
cd ../app
fab -H $SERVER_HOST
```
### 构建docker

```sh
cd docker/build
sh build.sh
#docker run backend_server:$tag
```

### GraphQL 接口测试地址
主要是将 jwt token 注入到 headers 里面，方便识别
> http://127.0.0.1:8080/graphiql.html?headers={%22auth%22:%22eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.MQ.3GLuAeZtpOfdC1_q_9jqe-EtmZcO2ZZIvPb5gLuMrp0%22}

### TODO:
基本上都会放入 issue， 要做的事情乱七八糟的。


### flyway-sbt无法download问题

在`~/.sbt/repositories` 中添加`flyway: https://flywaydb.org/repo`
