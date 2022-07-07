package com.example.service;

import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.example.domain.CustomLicenseParamExt;
import com.example.service.dto.AbstractServerDTO;
import com.example.service.dto.LinuxServerDTO;
import com.example.service.dto.MacOsServerDTO;
import com.example.service.dto.WindowsServerDTO;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: null.java
 * @Package com.example
 * @Description: 获取服务器配置信息
 * @date 2022/7/7 下午4:40
 */
public class ServerInfoService {
    public CustomLicenseParamExt getServerInfo() {
        AbstractServerDTO abstractServerInfo;

        //根据不同操作系统类型选择不同的数据获取方法
        OsInfo osInfo = SystemUtil.getOsInfo();
        if (osInfo.isWindows()) {
            abstractServerInfo = new WindowsServerDTO();
        } else if (osInfo.isMac()) {
            abstractServerInfo = new MacOsServerDTO();
        } else {
            //其他服务器类型
            abstractServerInfo = new LinuxServerDTO();
        }

        return abstractServerInfo.getServerInfos();
    }
}
