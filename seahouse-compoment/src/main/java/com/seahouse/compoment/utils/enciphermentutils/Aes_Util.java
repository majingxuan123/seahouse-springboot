package com.seahouse.compoment.utils.enciphermentutils;

import net.iharder.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * aes加密工具类
 */

@Configuration  //保证该类会被扫描到
@ConfigurationProperties(prefix = "aes")   //读取前缀为 config 的内容
@PropertySource("classpath:project.properties")  //定义了要读取的properties文件的位置
@Component
public class Aes_Util {
    /**
     *
     */
    private static final String AES = "AES";

    @Value("${aes_Key}")
    private String aes_Key;

    public String getAes_Key() {
        return aes_Key;
    }

    /**
     * 传入信息 和KEY 通过方法加密
     *
     * @throws Exception
     */
    public String aesEncrypt(String msg) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getAes_Key().getBytes(), AES);
        // 加密工具
        Cipher cipher = Cipher.getInstance(AES);
        // 初始化加密工具
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        // 开启加密工具
        byte[] result = cipher.doFinal(msg.getBytes());
        return new String(Base64.encodeBytes(result));
    }

    /**
     * 传入信息 和KEY 通过方法解密
     *
     * @throws Exception
     */
    public String aesDecrypt(String msg) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(getAes_Key().getBytes(), AES);
        // 加密工具
        Cipher cipher = Cipher.getInstance(AES);
        // 初始化加密工具
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        // 开启加密工具   解密之前先用base64解码
        String answer = new String(cipher.doFinal(Base64.decode(msg.getBytes())));
        return answer;
    }


}
