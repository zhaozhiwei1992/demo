FROM ubuntu:16.04
MAINTAINER examples@docker.com
RUN echo "deb http://archive.ubuntu.com/ubuntu precise main universe" > /etc/apt/sources.list
RUN apt-get update
RUN apt-get upgrade -y
#安装 ssh、apache 和 supervisor

#RUN apt-get install -y --force-yes perl-base=5.14.2-6ubuntu2
RUN apt-get install -y apache2.2-common
RUN apt-get install -y openssh-server apache2 supervisor
RUN mkdir -p /var/run/sshd
RUN mkdir -p /var/log/supervisor

#这里安装 3 个软件，还创建了 2 个 ssh 和 supervisor 服务正常运行所需要的目录。

COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf

#添加 supervisord 的配置文件，并复制配置文件到对应目录下面。

EXPOSE 22 80
CMD ["/usr/bin/supervisord"]
