package org.feasy.cloud.mysql.util;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;


/**
 * 数据库密码加密工具.
 * @author yangxiaohui
 */
@Slf4j
public class JasyptUtil {

    /**
     * 加密密码
     * @param publicKey 公钥
     * @param password  密码
     * @return 加密字符串
     */
    public static String encryptPwd(String publicKey, String password) {
        PooledPBEStringEncryptor encryptor =new PooledPBEStringEncryptor();
        encryptor.setConfig(cryptOr(publicKey));
        return encryptor.encrypt(password);
    }
    /**
     * 解密密码
     * @param publicKey 公钥
     * @param password  加密密码
     * @return 解密字符串
     */
    public static String decyptPwd(String publicKey, String password) {
        PooledPBEStringEncryptor encryptOr = new PooledPBEStringEncryptor();
        encryptOr.setConfig(cryptOr(publicKey));
        return encryptOr.decrypt(password);
    }
    private static SimpleStringPBEConfig cryptOr(String password) {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(password);
        config.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        return config;
    }


    public static void main(String[] args)throws Exception {
        //数据库密码加密
        System.out.println(encryptPwd("24D69E53E38C4BA6A79BB6BC2810CDB5","yxh123.."));
        System.out.println(decyptPwd("24D69E53E38C4BA6A79BB6BC2810CDB5","va0G7Pm2aWaD/Hu1GFt081uJV8j104X9"));
    }
}
