# 基于j8
FROM java:8

# 将本地文件挂载到容器
VOLUME /tmp

# 复制到工作目录
ADD config-server-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'

# 声明暴露端口
EXPOSE 7070

# 容器启动后执行命令
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
