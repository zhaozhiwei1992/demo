package com.lx.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * {@see https://www.jianshu.com/p/50449dde7f2b}
 * 1. 引入TkMybatis的Maven依赖
 * 2. 实体类的相关配置,@Id,@Table
 * 3. Mapper继承tkMabatis的Mapper接口
 * 4. 启动类Application或自定义Mybatis配置类上使用@MapperScan注解扫描Mapper接口
 * 5. 在application.properties配置文件中,配置mapper.xml文件指定的位置[可选]
 * 6. 使用TkMybatis提供的sql执行方法
 * <p>
 * PS :
 * 1. TkMybatis默认使用继承Mapper接口中传入的实体类对象去数据库寻找对应的表,因此如果表名与实体类名不满足对应规则时,会报错,这时使用@Table为实体类指定表。(这种对应规则为驼峰命名规则)
 * 2. 使用TkMybatis可以无xml文件实现数据库操作,只需要继承tkMybatis的Mapper接口即可。
 * 3. 如果有自定义特殊的需求,可以添加mapper.xml进行自定义sql书写,但路径必须与步骤4对应。
 * <p>
 * 6. 如有需要,实现mapper.xml自定义sql语句
 */
@SpringBootApplication
@MapperScan("com.lx.demo.mapper")
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}
