create table t_user
(
  id          int auto_increment
    primary key,
  name        varchar(20) null,
  password    varchar(50) null,
  realname    varchar(20) null,
  avatar      varchar(20) null,
  mobile      varchar(20) null,
  sex         varchar(5)  null,
  status      int         null,
  create_time mediumtext  null,
  constraint t_user_id_uindex
  unique (id)
);

INSERT INTO user.t_user (id, name, password, realname, avatar, mobile, sex, status, create_time) VALUES (1, '张三', '11', 'zhangsansan', 'xx', '12345678', 'MAN', null, '1536138000');

-- # UNIX时间戳转换为日期用函数： FROM_UNIXTIME()
select FROM_UNIXTIME(1536148611);

-- # 日期转换为UNIX时间戳用函数： UNIX_TIMESTAMP()
Select UNIX_TIMESTAMP('2018-09-05 17：01：34');

