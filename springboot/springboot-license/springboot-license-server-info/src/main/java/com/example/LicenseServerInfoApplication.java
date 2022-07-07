package com.example;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.example.domain.LicenseParamExt;
import com.example.service.ServerInfoService;
import com.example.service.dto.AbstractServerDTO;
import com.example.service.dto.LinuxServerDTO;
import com.example.service.dto.MacOsServerDTO;
import com.example.service.dto.WindowsServerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户获取服务器的硬件信息
 *
 * 1. mvn clean install 打好jar包
 * 2. 提供给客户, 服务器执行, 获取服务器信息
 * 3. java -jar springboot-license-server-info-0.0.1-SNAPSHOT.jar
 *
 *  服务器信息: {
 *     "mainBoardSerial": "41983BBB",
 *     "macAddress": [
 *         "02-42-E4-43-62-57",
 *         "02-42-4A-B2-CE-B9",
 *         "02-42-53-1C-F0-C0",
 *         "8C-16-45-32-C5-FF"
 *     ],
 *     "cpuSerial": "EA 06 08 00 FF FB EB BF",
 *     "ipAddress": [
 *         "172.19.0.1",
 *         "172.18.0.1",
 *         "172.17.0.1",
 *         "10.10.15.159"
 *     ]
 * }
 */
@SpringBootApplication
public class LicenseServerInfoApplication {

    private static final Logger log = LoggerFactory.getLogger(LicenseServerInfoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LicenseServerInfoApplication.class, args);

        final ServerInfoService serverInfoService = new ServerInfoService();

        final LicenseParamExt serverInfo = serverInfoService.getServerInfo();

        final String s = JSONUtil.toJsonPrettyStr(serverInfo);

        log.info("复制下述信息， 提供给服务提供商");
        log.info("服务器信息: {}", s);
    }

}
