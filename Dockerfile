#指定基础镜像，在其上进行定制
FROM jdk17

#维护者信息
MAINTAINER ljm

#这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层
VOLUME /tmp

#复制上下文目录下的target/demo-1.0.0.jar 到容器里
ADD target/project-gt-1.0-SNAPSHOT.jar project-gt.jar

#bash方式执行，使demo-1.0.0.jar可访问
#RUN新建立一层，在其上执行这些命令，执行结束后， commit 这一层的修改，构成新的镜像。
#RUN bash -c "touch /app.jar"
#声明运行时容器提供服务端口，这只是一个声明，在运行时并不会因为这个声明应用就会开启这个端口的服务
EXPOSE 7700
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
#指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
#ENTRYPOINT ["java","-jar","app.jar"]
ENTRYPOINT exec java ${JAVA_OPTS} -Duser.timezone=GMT+08 -Dfile.encoding=UTF-8 -Dspring.profiles.active=prod  -jar  /project-gt.jar --mpw.key=${ENCRY}
