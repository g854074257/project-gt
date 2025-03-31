FROM openjdk:8u131-jdk

WORKDIR application

#维护者信息
MAINTAINER gt

ADD target/project-gt.jar  /app.jar

EXPOSE 8848
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom -Duser.timezone=GMT+08 -Dfile.encoding=UTF-8","-jar","/app.jar","-Dspring.profiles.active=prod -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:-OmitStackTraceInFastThrow"]
ENTRYPOINT exec java ${JAVA_OPTS} -Duser.timezone=GMT+08 -Dfile.encoding=UTF-8 -jar -Dspring.profiles.active=dev -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:-OmitStackTraceInFastThrow /app.jar