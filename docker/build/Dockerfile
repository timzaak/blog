FROM java:openjdk-8-jdk-alpine

MAINTAINER timzaak<zsy.evan@gmail.com>

RUN apk add --update bash && rm -rf /var/cache/apk/*

expose 9000

ADD entrypoint.sh /entrypoint.sh

ADD backend-1.0 /server
ENTRYPOINT ["sh" "entrypoint.sh"]
