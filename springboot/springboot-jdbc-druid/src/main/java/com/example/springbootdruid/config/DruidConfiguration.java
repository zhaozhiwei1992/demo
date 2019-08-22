package com.example.springbootdruid.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DruidConfiguration {
    /**
     * 注册一个StatViewServlet
     *
     * @return
     */

    @Bean
    public ServletRegistrationBean DruidStatViewServle2() {

        //org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid" +
                "/*");

        //添加初始化参数：initParams
        //白名单：
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");

        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", "192.168.1.73");

        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");

        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");

        return servletRegistrationBean;

    }


    /**
     * 注册一个：filterRegistrationBean
     *
     * @return
     */

    @Bean
    public FilterRegistrationBean druidStatFilter2() {


        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());


        //添加过滤规则.

        filterRegistrationBean.addUrlPatterns("/*");


        //添加不需要忽略的格式信息.

        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid2/*");

        return filterRegistrationBean;

    }

    /**
     * 配置绑定
     * <p>
     * 这个玩意儿一定得有不然坑爹啊， sql监控的配置加载不进来
     * 感谢{@see https://www.tuicool.com/articles/MVbYriY}  配置类绑定数据源和配置信息。
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druid() {
        return new DruidDataSource();
    }

    /**
     * 注册dataSouce，这里作为一个例子，只注入了部分参数信息，其它的参数自行扩散思维。
     * 编程方式注入，　这种方式的配置会覆盖掉配置文件的配置
     *
     * @param driver
     * @param url
     * @param username
     * @param password
     * @param maxActive
     * @return
     */
//    @Bean
    public DataSource druidDataSource(@Value("${spring.datasource.driverClassName}") String driver,

                                      @Value("${spring.datasource.url}") String url,

                                      @Value("${spring.datasource.username}") String username,

                                      @Value("${spring.datasource.password}") String password,

                                      @Value("${spring.datasource.maxActive}") int maxActive

    ) {

        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setDriverClassName(driver);

        druidDataSource.setUrl(url);

        druidDataSource.setUsername(username);

        druidDataSource.setPassword(password);

        druidDataSource.setMaxActive(maxActive);


        System.out.println("DruidConfiguration.druidDataSource(),url=" + url + ",username=" + username + ",password=" + password);

        try {

            druidDataSource.setFilters("stat, wall");

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return druidDataSource;

    }
}
