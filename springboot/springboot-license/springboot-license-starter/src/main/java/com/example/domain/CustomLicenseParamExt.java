package com.example.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 自定义的可被允许的服务器硬件信息的实体类（如果校验其他参数，可自行补充）
 * .备注：如果只需要检验文件的生效和过期时间无需创建此类。
 *
 * 这里要注意, 包路径啥的都不能变, 解析content反序列化时, 需要匹配
 * @author zhaozhiwei
 * @date 2021-05-25-13:34
 */
@Data
public class CustomLicenseParamExt implements Serializable {
    /**
     * 可被允许的IP地址
     */
    private List<String> ipAddress;

    /**
     * 可被允许的MAC地址
     */
    private List<String> macAddress;

    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;

    /**
     * 允许的数据库地址
     */
    private String databaseUrl;

    // TODO 如果还需增加其它项校验, 注意这里和license生成的项目都要同步增加字段
}
