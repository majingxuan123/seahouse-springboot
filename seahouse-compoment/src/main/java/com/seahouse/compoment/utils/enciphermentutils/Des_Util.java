package com.seahouse.compoment.utils.enciphermentutils;

import net.iharder.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.spec.KeySpec;


/**
 * <ul>
 * <li>文件名称: Des_Util</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2018/3/9 0009</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author majx
 * @version 1.0.0
 */
@Component
public class Des_Util {

    private static final String KEY_ALGORITHM = "DES";
    //如果写DES  默认也是这个              算法/工作模式/填充模式
    private static final String TRANSFORMATION = "DES/ECB/PKCS5Padding";

    @Value("${des_Key}")
    private String des_Key;

    public String getDes_Key() {
        return des_Key;
    }

    public void setDes_Key(String des_Key) {
        this.des_Key = des_Key;
    }

    /**
     * 传入信息 和KEY 通过方法加密
     *
     * @throws Exception
     */
    public String DesEncrypt(String msg) throws Exception {
        // 秘钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        // 秘钥的规格 通过秘钥的byte数组获得
        KeySpec keySpec = new DESKeySpec(getDes_Key().getBytes());
        // 秘钥工厂通过秘钥规格制造秘钥
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        // 加密工具
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // 初始化加密工具
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        // 开启加密工具
        byte[] result = cipher.doFinal(msg.getBytes());

//		System.out.println("加密后的数据----------："+new String(result));

        return new String(Base64.encodeBytes(result));
    }

    /**
     * 传入信息 和KEY 通过方法解密
     *
     * @throws Exception
     */
    public String DesDecrypt(String msg) throws Exception {
        // 秘钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        // 秘钥的规格 通过秘钥的byte数组获得
        KeySpec keySpec = new DESKeySpec(getDes_Key().getBytes());
        // 秘钥工厂通过秘钥规格制造秘钥
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        // 加密工具
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // 初始化加密工具
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        // 开启加密工具   解密之前先用base64解码
        String answer = new String(cipher.doFinal(Base64.decode(msg.getBytes())));
        return answer;
    }

}
