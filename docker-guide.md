# Docker 镜像构建指南 (JIB插件)

## 概述

本项目集成了Google的`jib-maven-plugin`，无需Docker守护进程即可构建容器镜像并推送到远程仓库。JIB插件的主要优势：

1. 无需Docker守护进程 - 直接从Maven构建镜像
2. 快速增量构建 - 只更新变化的层
3. 可重现的构建 - 相同输入产生相同的镜像
4. 优化的镜像分层 - 更好的缓存利用

## 前提条件

- JDK 17
- Maven 3.5+
- 阿里云容器镜像服务账号（或其他Docker注册表账号）

## 基本用法


由于在pom.xml中配置了`<execution>`标签，打包阶段会自动执行jib:build目标，构建并推送镜像到配置的远程仓库：
`crpi-xhn5r0iyr2p9qsm4.cn-chengdu.personal.cr.aliyuncs.com/guitao/base_project:1.0.0`


构建的tar文件可在`target/jib-image.tar`找到。

## 认证配置

### 配置阿里云容器镜像服务认证

在使用前需要配置认证信息，有两种方式：

#### 1. 使用Maven的settings.xml

编辑`~/.m2/settings.xml`文件，添加服务器认证信息：

```xml
<settings>
  <servers>
    <server>
      <id>aliyun-docker-registry</id>
      <username>您的阿里云用户名</username>
      <password>您的阿里云密码或访问令牌</password>
    </server>
  </servers>
</settings>
```

然后在pom.xml的jib配置中添加认证引用：

```xml
<plugin>
  <groupId>com.google.cloud.tools</groupId>
  <artifactId>jib-maven-plugin</artifactId>
  <configuration>
    <to>
      <image>crpi-xhn5r0iyr2p9qsm4.cn-chengdu.personal.cr.aliyuncs.com/guitao/base_project:1.0.0</image>
      <auth>
        <username>${aliyun.username}</username>
        <password>${aliyun.password}</password>
      </auth>
    </to>
  </configuration>
</plugin>
```

#### 2. 使用命令行参数

您也可以直接在命令行传递认证信息：

```bash
mvn jib:build -Djib.to.auth.username=您的用户名 -Djib.to.auth.password=您的密码
```

## 自定义构建配置

### 修改基础镜像

更改pom.xml中的`<from><image>`配置：

```xml
<from>
  <image>openjdk:17-slim-bullseye</image>
</from>
```

### 修改目标镜像

修改pom.xml中的以下属性来更改目标镜像信息：

```xml
<properties>
  <image.repository>您的仓库地址</image.repository>
  <image.name>您的镜像名称</image.name>
  <image.tag>您的标签</image.tag>
</properties>
```

### 环境变量和JVM参数

在pom.xml的jib配置中已经设置了以下内容：

```xml
<container>
  <jvmFlags>
    <jvmFlag>-Duser.timezone=GMT+08</jvmFlag>
    <jvmFlag>-Dfile.encoding=UTF-8</jvmFlag>
  </jvmFlags>
  <environment>
    <TZ>Asia/Shanghai</TZ>
  </environment>
  <!-- 其他配置 -->
</container>
```

如需添加Spring profiles或其他参数，可以在jvmFlags中添加：

```xml
<jvmFlag>-Dspring.profiles.active=prod</jvmFlag>
```

## 常见问题解决

### 推送失败

如果推送失败，通常是认证问题。请检查：

1. 确保阿里云容器镜像服务认证信息正确
2. 检查网络是否能够访问阿里云容器镜像服务
3. 确认是否有权限推送到指定的仓库

### 构建超时

对于大型项目，可能需要增加超时时间：

```bash
mvn jib:build -Djib.httpTimeout=60000
```

## 参考资料

- [JIB Maven Plugin官方文档](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin)
- [阿里云容器镜像服务文档](https://www.alibabacloud.com/help/zh/acr)
- [Spring Boot with Docker](https://spring.io/guides/topicals/spring-boot-docker/) 