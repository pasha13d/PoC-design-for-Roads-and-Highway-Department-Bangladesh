FROM openjdk:8-jre-alpine

MAINTAINER  Tanim tanim1109135@gmail.com

RUN apk update && apk --update add fontconfig ttf-dejavu

RUN apk add --no-cache tzdata
ENV TZ Asia/Dhaka

WORKDIR /app

COPY . /app

EXPOSE <http_app_port> <http_admin_port>

CMD ["./bin/<name>-container"]
