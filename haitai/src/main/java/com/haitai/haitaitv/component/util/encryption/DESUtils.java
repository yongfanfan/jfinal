package com.haitai.haitaitv.component.util.encryption;

import com.haitai.haitaitv.component.constant.OtherConsts;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.util.Base64;

/**
 * DES加密
 */
public class DESUtils {

    public static final DESUtils INSTANCE = new DESUtils();

    // 指定DES加密解密所用的密钥
    private static Key key;

    /**
     * 加密key为空
     */
    public DESUtils() {
        setkey("haitaitv");
    }

    /**
     * 设置加密key
     *
     * @param keyStr 加密key值
     */
    public DESUtils(String keyStr) {
        setkey(keyStr);
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        /*DESUtils des = new DESUtils("aaaaaaaa");
        String test = des.encryptString("你好123123asdasdasd阿三大声大声道，。，，。，");
        System.out.println(test);
        System.out.println(des.decryptString(test));*/
    }

    /**
     * 加密
     */
    public static String passwordEncrypt(String password) {
        return INSTANCE.encryptString(password);
    }

    /**
     * 解密
     */
    public static String passwordDecrypt(String encryptPassword) {
        return INSTANCE.decryptString(encryptPassword);
    }

    /**
     * 默认密码
     */
    public static String getDefaultPassword() {
        return passwordEncrypt(OtherConsts.DEFAULT_PASSWORD);
    }

    /**
     * 设置加密的校验码
     */
    private void setkey(String keyStr) {
        try {
            DESKeySpec objDesKeySpec = new DESKeySpec(keyStr.getBytes("UTF-8"));
            SecretKeyFactory objKeyFactory = SecretKeyFactory.getInstance("DES");
            key = objKeyFactory.generateSecret(objDesKeySpec);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 对字符串进行DES加密，返回BASE64编码的加密字符串
    public final String encryptString(String str) {

        byte[] bytes = str.getBytes();
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(bytes);
            return Base64.getEncoder().encodeToString(encryptStrBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 对BASE64编码的加密字符串进行解密，返回解密后的字符串
    public final String decryptString(String str) {
        try {
            byte[] bytes = Base64.getDecoder().decode(str);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            bytes = cipher.doFinal(bytes);
            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // 对字符串进行DES加密，返回BASE64编码的加密字符串，并且对url安全
    public final String encryptStringForUrl(String str) {

        byte[] bytes = str.getBytes();
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(bytes);
            return Base64.getUrlEncoder().encodeToString(encryptStrBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 对BASE64编码的加密字符串进行解密，返回解密后的字符串，被加密的串应是使用encryptStringForUrl加密的
    public final String decryptStringForUrl(String str) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(str);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            bytes = cipher.doFinal(bytes);
            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
