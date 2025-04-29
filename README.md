# project-gt
基于springboot3.0 + jdk17 搭建的基础项目框架，引入了基础依赖，可以基于该项目框架快速搭建业务系统

## 功能特性

- 基于SpringBoot 3.0和JDK 17
- 集成MyBatis-Plus、Redis、RabbitMQ等常用组件
- **支持容器化构建**：使用Google JIB插件构建镜像并推送到阿里云容器镜像服务

## 容器镜像构建

本项目集成了Google的JIB Maven插件，无需Docker环境即可构建容器镜像：

生成的镜像将自动推送到：
`crpi-xhn5r0iyr2p9qsm4.cn-chengdu.personal.cr.aliyuncs.com/guitao/base_project:1.0.0`

更多容器镜像构建相关信息，请参考[Docker镜像构建指南](./docker-guide.md)。
