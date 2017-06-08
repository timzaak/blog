### Server Starter
目前用到的技术选型，如下
> 数据交互格式： Graphql

> 框架：akka-http sangrid

> 语言：scala2.12

> 数据库： postgrel

> 库： slick, json4s, flyway

> 开发环境: docker

> 部署工具: python3 Fabric3

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

### 接口测试地址

> http://127.0.0.1:8080/graphiql.html?headers={%22auth%22:%22eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.MQ.3GLuAeZtpOfdC1_q_9jqe-EtmZcO2ZZIvPb5gLuMrp0%22}

### TODO:

> 服务监控

>  微服务（发现，队列，定时）


### flyway-sbt无法download问题

在`~/.sbt/repositories` 中添加`flyway: https://flywaydb.org/repo`
