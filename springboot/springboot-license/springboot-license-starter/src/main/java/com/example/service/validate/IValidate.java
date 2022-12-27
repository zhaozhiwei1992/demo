package com.example.service.validate;

import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.example.service.dto.HardWareParamDTO;
import com.example.service.dto.AbstractServerDTO;
import com.example.service.dto.LinuxServerDTO;
import com.example.service.dto.MacOsServerDTO;
import com.example.service.dto.WindowsServerDTO;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;

/**
 * @Title: null.java
 * @Package: com.example.service.validate
 * @Description: 校验接口, 通过查找其, 实现进行循环校验
 * @author: zhaozhiwei
 * @date: 2022/12/16 下午2:26
 * @version: V1.0
 */
public interface IValidate {

    void validate(final LicenseContent content) throws LicenseContentException;

    /**
     * 获取当前服务器需要额外校验的License参数
     *
     * @return
     */
    default HardWareParamDTO getServerDTO() {
        AbstractServerDTO abstractServerDTO;

        //根据不同操作系统类型选择不同的数据获取方法
        OsInfo osInfo = SystemUtil.getOsInfo();
        if (osInfo.isWindows()) {
            abstractServerDTO = new WindowsServerDTO();
        } else if (osInfo.isMac()) {
            abstractServerDTO = new MacOsServerDTO();
        } else {//其他服务器类型
            abstractServerDTO = new LinuxServerDTO();
        }

        return abstractServerDTO.getServerInfos();
    }
}
