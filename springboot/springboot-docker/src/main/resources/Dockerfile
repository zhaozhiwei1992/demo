FROM frolvlad/alpine-oraclejdk8
MAINTAINER zhaozhiweishanxi@gmail.com
ARG JAR_FILE
# 1. FROM 之后还要使用该参数的话，需要重新引入一下
ADD /$JAR_FILE //
# 如果需要在运行过程中使用，将该变量转换到环境变量中
ENV JAR_FILE=$JAR_FILE
# 测试参数
ARG my_arg
ENV my_env_var=$my_arg
# entrypoint测试无法直接使用环境变量值，　直接生成shell脚本执行
RUN echo "java -Djava.security.egd=file:/dev/./urandom -jar /$JAR_FILE" > /run_module.sh
ENTRYPOINT ["sh","/run_module.sh"]
# VOLUME /tmp

# 构建
# docker build --build-arg my_arg=foo --build-arg JAR_FILE=springboot-docker-0.0.1-SNAPSHOT.jar -t springboot-docker-0.0.1 .