

## 📚简介

🔥基于G2Plot、Echarts的大屏设计器，具备设计、预览能力，支持MySQL、Oracle、PostgreSQL、Groovy等数据集接入

-------------------------------------------------------------------------------

## 📝文档

[📘中文文档](https://www.yuque.com/chuinixiongkou/bigscreen/index)

-------------------------------------------------------------------------------

## 🛠️快速使用

#### 初始化SQL

执行 doc/init.sql

#### 修改配置文件

修改 src/main/resource 目录下的 application-dev.yml 中的数据库连接信息

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driverClassName: com.p6spy.engine.spy.P6SpyDriver
    url: jdbc:p6spy:mysql://localhost:3306/database?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false
    username: 用户名
    password: 密码
```

#### 运行启动类

运行 src/main/java/com/gccloud/BigScreenApplication 类中的main方法

-------------------------------------------------------------------------------

## 📦安装

### 🍊Maven

在项目的pom.xml的dependencies中加入以下内容:

```xml
<dependency>
	<groupId>com.gccloud.starter.lowcode</groupId>
	<artifactId>big-screen</artifactId>
	<packaging>pom</packaging>
</dependency>
```

