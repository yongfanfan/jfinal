package com.haitai.haitaitv.component.shiro.credentials;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.haitai.haitaitv.component.util.encryption.DES3Utils;

/**
 * 自定义matcher,用于校验用户密码
 *
 * @author liuzhou
 *         create at 2016-11-25 14:55
 */
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    protected Object getCredentials(AuthenticationToken token) {
        return DES3Utils.INSTANCE.encryptString(new String((char[]) super.getCredentials(token)));
    }

    @Override
    protected Object getCredentials(AuthenticationInfo info) {
        return super.getCredentials(info);
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        return super.doCredentialsMatch(token, info);
    }
}
