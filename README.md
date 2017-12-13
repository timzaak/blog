### Server Starter
请注意，目前正在加紧学习并实现各种大 Feature ，下面的启动方式以及文档可能更新不及时。具体实现 Feature 参看 issues.

目前用到的技术选型，如下
> 数据交互格式： Graphql

> 框架：akka系列, sangrid , Vue

> 语言：scala2.12

> 数据库： postgres

> 库： slick, json4s, flyway

> 容器: docker

> 部署工具: python3 Fabric3


### 代码组织方式

> app 目录下是主要的 bootstrap 、业务逻辑和未成熟的组件，基于 git submodule 来做组件分离代码管理，凡是成熟的组件尽量脱离 app 目录。

> docker 目录存放一些 docker 配置，以及开发依赖的环境。

> script 目录存放一些服务学习脚本。目前是 js 代码。

> benchmark 目录存放性能测试代码。

> cluster 目录存放和集群相关的地方

> frontend 目录存放前端相关代码


### 单机开始
```sh
git submodule init
git submodule update
#init docker container
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
需要将 jwt token 注入到 headers 里面，方便识别
> http://127.0.0.1:8080/graphiql.html?headers={%22auth%22:%22eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.MQ.3GLuAeZtpOfdC1_q_9jqe-EtmZcO2ZZIvPb5gLuMrp0%22}

### WebSocket akka 集群
目前 WS akka 集群的基本功能做完了，但还有很多生产环境需要的 feature 还没做。所以目前仅供探索。具体关于其 WS集群 的思考请参照 [#19](https://github.com/timzaak/graphql-backend-starter/issues/19)

#### 运行 WS akka 集群
app/src/main/resources/application.conf 添加 `include "cluster.conf"`
```sh
cd cluster
# run akka seeder， 启动一个
sbt "runMain Seed -Dconfig.resource=seed.conf"
# run akka ws session  ，按需求，可以启动 n 多个
sbt "runMain Session -Dconfig.resource=session.conf"
```

#### 前端 Vue 单页应用
```sh
yarn install
npm run dev
```

### TODO:
基本上都会放入 issue， 要做的事情乱七八糟的。


### flyway-sbt无法download问题

在`~/.sbt/repositories` 中添加`flyway: https://flywaydb.org/repo`
