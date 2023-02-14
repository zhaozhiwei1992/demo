package com.example.service;

import cn.hutool.extra.spring.SpringUtil;
import com.example.service.validate.IValidate;
import de.schlichtherle.license.*;
import de.schlichtherle.xml.GenericCertificate;
import lombok.extern.slf4j.Slf4j;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * @author zhaozhiwei
 * @version V1.0
 * @Title: LicenseManagerHolder
 * @Package com/example/service/LicenseManagerHolder.java
 * @Description: 校验入口
 * @date 2022/12/16 下午2:41
 */
public class LicenseManagerHolder {
    private static LicenseManager licenseManager;

    public static synchronized LicenseManager getInstance(LicenseParam licenseParam) {
        if (null == licenseManager) {
            try {
                licenseManager = new CustomLicenseManager(licenseParam);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return licenseManager;

    }

    /**
     * 自定义CustomLicenseManager继承LicenseManager，实现自定义的参数校验。
     */
    @Slf4j
    static class CustomLicenseManager extends LicenseManager {
        /**
         * XML编码
         */
        private static final String XML_CHARSET = "UTF-8";
        /**
         * 默认BUFSIZE
         */
        private static final int DEFAULT_BUFSIZE = 8 * 1024;

        public CustomLicenseManager() {

        }

        public CustomLicenseManager(LicenseParam param) {
            super(param);
        }

        /**
         * 复写create方法
         *
         * @param content
         * @param notary
         * @return
         * @throws Exception
         */
        @Override
        protected synchronized byte[] create(
                LicenseContent content,
                LicenseNotary notary)
                throws Exception {
            initialize(content);
            this.validateCreate(content);
            final GenericCertificate certificate = notary.sign(content);
            return getPrivacyGuard().cert2key(certificate);
        }


        /**
         * 复写install方法，其中validate方法调用本类中的validate方法，校验IP地址、Mac地址等其他信息
         *
         * @param key
         * @param notary
         * @return
         * @throws Exception
         */
        @Override
        protected synchronized LicenseContent install(final byte[] key, final LicenseNotary notary) throws Exception {
            final GenericCertificate certificate = getPrivacyGuard().key2cert(key);
            notary.verify(certificate);
            final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
            this.validate(content);
            setLicenseKey(key);
            setCertificate(certificate);

            return content;
        }


        /**
         * 复写verify方法，调用本类中的validate方法，校验IP地址、Mac地址等其他信息
         *
         * @param notary
         * @return
         * @throws Exception
         */
        @Override
        protected synchronized LicenseContent verify(final LicenseNotary notary)
                throws Exception {

            // Load license key from preferences,
            final byte[] key = getLicenseKey();
            if (null == key) {
                throw new NoLicenseInstalledException(getLicenseParam().getSubject());
            }

            GenericCertificate certificate = getPrivacyGuard().key2cert(key);
            notary.verify(certificate);
            final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
            this.validate(content);
            setCertificate(certificate);

            return content;
        }

        /**
         * 校验生成证书的参数信息
         *
         * @param content
         * @throws LicenseContentException
         */
        protected synchronized void validateCreate(final LicenseContent content)
                throws LicenseContentException {
            final LicenseParam param = getLicenseParam();

            final Date now = new Date();
            final Date notBefore = content.getNotBefore();
            final Date notAfter = content.getNotAfter();
            if (null != notAfter && now.after(notAfter)) {
                throw new LicenseContentException("证书失效时间不能早于当前时间");
            }
            if (null != notBefore && null != notAfter && notAfter.before(notBefore)) {
                throw new LicenseContentException("证书生效时间不能晚于证书失效时间");
            }
            final String consumerType = content.getConsumerType();
            if (null == consumerType) {
                throw new LicenseContentException("用户类型不能为空");
            }
        }


        /**
         * 复写validate方法，增加IP地址、Mac地址等其他信息校验
         *
         * @param content
         * @throws LicenseContentException
         */
        @Override
        protected synchronized void validate(final LicenseContent content)
                throws LicenseContentException {
            //1 调用父类的validate方法
            super.validate(content);

            //2 自定义license校验
            final Map<String, IValidate> beansOfType =
                    SpringUtil.getApplicationContext().getBeansOfType(IValidate.class);
            for (Map.Entry<String, IValidate> iValidateEntry : beansOfType.entrySet()) {
                final IValidate validateBean = iValidateEntry.getValue();
                validateBean.validate(content);
            }
        }

        /**
         * 重写XMLDecoder解析XML
         *
         * @param encoded
         * @return
         */
        private Object load(String encoded) {
            BufferedInputStream inputStream = null;
            XMLDecoder decoder = null;
            try {
                inputStream = new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(XML_CHARSET)));

                decoder = new XMLDecoder(new BufferedInputStream(inputStream, DEFAULT_BUFSIZE), null, null);

                return decoder.readObject();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (decoder != null) {
                        decoder.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e) {
                    log.error("XMLDecoder解析XML失败", e);
                }
            }
            return null;
        }
    }
}
