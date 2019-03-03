package com.silverbullet.security;

import com.silverbullet.security.rsa.RSAUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Base64Utils;

/**
 * 加密方法
 * Created by jeffyuan on 2018/12/5.
 */
public class SilverbulletEncrypt {

    @Value("${silverbullet.rsa.privatekey}")
    private String rsaPrivateKey;
    @Value("${silverbullet.rsa.charset}")
    private String rsaCharSet;

    /**
     * 对字符串进行加密
     * @param securityMethod
     * @param content
     * @return
     * @throws Exception
     */
    public String decrypt(SecurityMethod securityMethod, String content) throws Exception {
        if (securityMethod == SecurityMethod.NULL) {
            return content;
        }

        switch (securityMethod) {
            case RSA:
                return new String(RSAUtils.decryptByPrivateKey(Base64Utils.decodeFromString(content),
                        rsaPrivateKey), this.rsaCharSet);
        }

        return content;
    }

    /**
     * 对字符串进行解密
     * @param securityMethod
     * @param content
     * @return
     * @throws Exception
     */
    public String encrypt(SecurityMethod securityMethod, String content) throws Exception {

        if (securityMethod == SecurityMethod.NULL) {
            return content;
        }

        switch (securityMethod) {
            case RSA:
                //进行加密操作
                byte[] data = content.getBytes();
                byte[] encodedData = RSAUtils.encryptByPrivateKey(data, rsaPrivateKey);
                String result = Base64Utils.encodeToString(encodedData);
                return result;
        }

        return content;
    }
}
