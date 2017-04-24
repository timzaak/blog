### Server Starter
目前用到的技术选型，如下
> 数据交互格式： Graphql

> 框架：akka-http sangrid

> 语言：scala2.12

> 数据库： postgrel

> 库： slick, json4s, flyway

> 开发环境: docker

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
### TODO:

> 日志管理

> 服务监控

> akka cluster 微服务（发现，队列，定时）

> 输入数据校验

> Relay??
