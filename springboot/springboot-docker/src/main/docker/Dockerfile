#FROM java:8
FROM frolvlad/alpine-oraclejdk8
MAINTAINER zhaozhiweishanxi@gmail.com
ADD /springboot-docker-0.0.1-SNAPSHOT.jar //
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/springboot-docker-0.0.1-SNAPSHOT.jar"]
