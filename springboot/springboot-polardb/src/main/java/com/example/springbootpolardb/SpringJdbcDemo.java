package com.example.springbootpolardb;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootpolardb
 * @Description:
 *
 * create table foo(id int, name varchar(20), amt number(18,2)); // 如果创建int类型字段, 并且传入类型不一致报错
 * Caused by: java.sql.BatchUpdateException: Batch entry 0 insert into foo (ID,NAME,AMT) values('1','zhangsan','100
 * .00'::numeric) was aborted: ERROR: column "id" is of type integer but expression is of type character varying
 * @date 2021/5/18 下午7:22
 */
@ComponentScan(basePackages = "com.example.springbootpolardb")
public class SpringJdbcDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

        // 注册 Configuration Class
        context.register(SpringJdbcDemo.class);

        // 启动 Spring 应用上下文
        context.refresh();

        // 获取jdbc datasource
        DataSource dataSource = context.getBean(DataSource.class);
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        final List<Map<String, Object>> list = new ArrayList<>();
        // 初始化数据
        final Map<String, Object> map = new HashMap<>();
//        map.put("id", "2");
        map.put("name", "zhangsan");
        map.put("amt", new BigDecimal("100.00"));
        list.add(map);

//        Object[] cols = Arrays.asList("ID", "NAME", "AMT").toArray();
        Object[] cols = Arrays.asList("NAME", "AMT").toArray();

        String col = null;
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();

        for(int i=0;i<cols.length;i++){
            col = (String)cols[i];
            if(i==0){
                s1.append(col);
                s2.append("?");
            }else{
                s1.append(",").append(col);
                s2.append(",?");
            }
        }
        String sql = "insert into foo ("+s1+") values("+s2+")";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return list.size();
            }
            public void setValues(PreparedStatement ps, int i)throws SQLException {
                Map<String,Object> dto = list.get(i);
                String col = null;
                for(int j=0;j<cols.length;j++){
                    col = (String)cols[j];
                    if("AMT".equals(col)){
                        ps.setBigDecimal(j+1,new BigDecimal(String.valueOf(dto.get("amt"))));
//                    }else if("ID".equals(col)){
//                        ps.setInt(j+1, Integer.parseInt(String.valueOf(dto.get("id"))));
                    }else{
                        ps.setString(j+1, dto.get(col.toLowerCase())==null?null:dto.get(col.toLowerCase()).toString());
                    }
                }
            }
        });

        // 关闭 Spring 应用上下文
        context.close();

    }
}
