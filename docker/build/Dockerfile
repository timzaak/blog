FROM java:openjdk-8-jdk-alpine

MAINTAINER timzaak<zsy.evan@gmail.com>

RUN apk add --update bash && rm -rf /var/cache/apk/*

expose 9000

copy entrypoint.sh /entrypoint.sh
ADD backend-1.0 /server

ENTRYPOINT ["/entrypoint.sh"]
