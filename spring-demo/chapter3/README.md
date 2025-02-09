# Spring 徹底入門 第3章 デモ

## 環境構築

Docker で DB サーバーをを起動する。

```sh
$ cd docker
$ docker-compose up -d
```

DB への接続情報は [application.properties](src/main/resources/application.properties) に記述している。

## 整形

```sh
./gradlew spotlessApply
```
