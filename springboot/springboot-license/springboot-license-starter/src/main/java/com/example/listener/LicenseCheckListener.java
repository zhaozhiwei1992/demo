package com.example.listener;

import com.example.configuration.CustomLicenseParam;
import com.example.service.LicenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: LicenseCheckListener
 * @Package com/example/listener/LicenseCheckListener.java
 * @Description: 启动服务时，这里会获取到license的配置信息,
 * 然后去解析license的配置, 进行安装
 * @date 2022/7/8 上午9:59
 */
@Slf4j
@Component
public class LicenseCheckListener implements CommandLineRunner {

    @Autowired
    private LicenseService licenseService;


    @Override
    public void run(String... args) throws Exception {
        licenseService.install();
    }
}
