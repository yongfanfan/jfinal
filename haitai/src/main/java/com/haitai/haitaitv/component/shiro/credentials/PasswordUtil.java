package com.haitai.haitaitv.component.shiro.credentials;

import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.component.constant.OtherConsts;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author liuzhou
 *         create at 2017-08-02 12:08
 */
public class PasswordUtil {

    public static String generateSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }

    public static String generatePassword(String username, String password, String salt) {
        // 此处的加密规则需要和shiro.ini设置的规则保持一致
        SimpleHash hash = new SimpleHash("md5", password, username + salt, 2);
        return hash.toHex();
    }

    /**
     * 检查用户的密码是否为默认密码
     */
    public static boolean isDefault(SysUser user) {
        return isMatch(user, OtherConsts.DEFAULT_PASSWORD);
    }

    /**
     * 检查密码是否匹配
     *
     * @param user     用户
     * @param password 前端传入的将明文密码经过一次md5后的字符串
     * @return true则匹配
     */
    public static boolean isMatch(SysUser user, String password) {
        String generatePassword = generatePassword(user.getUsername(), password, user.getSalt());
        return generatePassword.equals(user.getPassword());
    }

    public static void main(String[] args) {
        // 初始用户的密码可在此生成
        // 若用户名取 harrid@qq.com ，密码取 1717888 ，盐取 9132a7479eeb83fe9753b495a34a5623
        // 则加密后的密码为 20c9fda4209978b770a51a18c4a4af13
        String password = new Md5Hash("1717888").toString();
        System.out.println(password);
        System.out.println(generatePassword("harrid@qq.com", password,
                "9132a7479eeb83fe9753b495a34a5623"));
    }
}
