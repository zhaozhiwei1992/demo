package com.example;

import com.example.annotation.DevQualifiter;
import com.example.annotation.ProdQualifiter;
import com.example.domain.CommonBean;
import com.example.domain.DevBean;
import com.example.domain.ProdBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example
 * @Description: TODO
 * @date 2022/2/17 上午9:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/spring-context.xml")
@ActiveProfiles(profiles = {"dev", "prod"})
public class ProfileActiveTest {

    @Autowired
    private ProdBean prodBean;

    @Autowired
    private DevBean devBean;

    @Test
    public void profileBeanTest(){
        Assert.notNull(prodBean, "prodBean初始化失败");
        Assert.notNull(devBean, "devBean初始化失败");
    }

    /**
     * 相同类型bean, 通过@Primary或者通过Qualifier来指定唯一
     */
    @Autowired
//    @Qualifier("devBean")
//    @DevQualifiter
    @ProdQualifiter
    private CommonBean commonBean;

    @Test
    public void commonBeanTest(){
        Assert.notNull(commonBean, "commonBean 初始化失败");
    }
}
