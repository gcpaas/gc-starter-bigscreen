### 如何运行

#### 1 初始化SQL

执行doc/init.sql即可

#### 2 修改配置文件

修改src/main/resource目录下的 application-dev.yml 中的数据库连接信息即可

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driverClassName: com.p6spy.engine.spy.P6SpyDriver
    url: jdbc:p6spy:mysql://localhost:3306/database?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false
    username: 用户名
    password: 密码
```

#### 3 运行启动类

运行src/main/java/com/gccloud/BigScreenApplication类中的main方法



### MAVEN

```xml
<dependency>
    <groupId>com.gccloud</groupId>
    <artifactId>gc-starter-bigscreen</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

