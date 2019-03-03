package com.silverbullet.security.rsa;


import com.silverbullet.utils.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

/**
 * RAS 秘钥操作
 * Created by jeffyuan on 2018/4/17.
 */
public class RSAUtilsPub {
    //加密算法RSA
    public static final String KEY_ALGORITHM = "RSA";
    //RSA最大解密密文大小
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static void main(String [] args) throws Exception {

        {
            FileInputStream inputStream = new FileInputStream(new File("pub.key"));
            byte [] byteRed = new byte[inputStream.available()];
            inputStream.read(byteRed);

            inputStream.close();

            String publicKey = new String(byteRed);

            inputStream = new FileInputStream(new File("licence"));
            byte[] encodedData = new byte[inputStream.available()];
            inputStream.read(encodedData);
            inputStream.close();


            byte[] decodedData = RSAUtilsPub.decryptByPublicKey(encodedData, publicKey);
            System.out.println("解密后文字: " + new String(decodedData));
        }

    }
}
