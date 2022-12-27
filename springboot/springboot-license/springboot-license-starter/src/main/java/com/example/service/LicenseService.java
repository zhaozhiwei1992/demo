package com.example.service;

import com.example.configuration.CustomLicenseParam;
import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

/**
 * License 安装 校验 处理类
 *
 * @author zhaozhiwei
 */
@Slf4j
@Service
public class LicenseService {

    @Autowired
    private CustomLicenseParam customLicenseParam;

    public synchronized LicenseContent install() {
        log.info("证书配置信息 {}", customLicenseParam);
        if (StringUtils.isEmpty(customLicenseParam.getLicensePath())) {
            throw new RuntimeException("++++++++ 未配置License证书 ++++++++");
        }

        LicenseContent result;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //1. 安装证书
        try {
            LicenseManager licenseManager = LicenseManagerHolder.getInstance(initLicenseParam());
            licenseManager.uninstall();
            File file = new File(customLicenseParam.getLicensePath());
            result = licenseManager.install(file);
            log.info(MessageFormat.format("证书安装成功，证书有效期：{0} - {1}", format.format(result.getNotBefore()),
                    format.format(result.getNotAfter())));
        } catch (Exception e) {
            log.error("证书安装失败！", e);
            throw new RuntimeException("++++++++ 证书安装失败 ++++++++");
        }

        return result;
    }


    /**
     * 校验License证书
     *
     * @return
     */
    public boolean verify() {
        LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //2. 校验证书
        try {
            LicenseContent licenseContent = licenseManager.verify();
            log.info(MessageFormat.format("证书校验通过，证书有效期：{0} - {1}", format.format(licenseContent.getNotBefore()),
                    format.format(licenseContent.getNotAfter())));
            return true;
        } catch (Exception e) {
            log.error("证书校验失败！", e);
            return false;
        }
    }


    /**
     * 初始化证书生成参数
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseService.class);
        CipherParam cipherParam = new DefaultCipherParam(customLicenseParam.getStorePass());
        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseService.class
                , customLicenseParam.getPublicKeysStorePath()
                , customLicenseParam.getPublicAlias()
                , customLicenseParam.getStorePass()
                , null);

        return new DefaultLicenseParam(customLicenseParam.getSubject()
                , preferences
                , publicStoreParam
                , cipherParam);
    }

}
