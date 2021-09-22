package com.example.springbootjpa.domain;

import javax.persistence.Column;
import javax.persistence.Version;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example.springbootjpa.domain
 * @Description: TODO
 * @date 2021/9/22 下午7:51
 */
public class BaseEntity {

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    @Id
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    @GeneratedValue(generator = "system-uuid")
//    @Column(name="ID", length =64)
//    protected Long id;

    @Column(name="CREATE_DATE_TIME",nullable=true)
    protected String createDateTime;

    /**
     * 增加版本，由hibernate自动控制数据并发
     * 不要手动更新该字段
     */
    @Version
    protected Integer version;


    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
