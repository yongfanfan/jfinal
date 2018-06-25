package com.haitai.haitaitv.component.shiro.realm;

import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.module.back.system.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * 自定义的realm
 *
 * @author liuzhou
 *         create at 2016-11-25 14:32
 */
public class MyRealm extends AuthorizingRealm {

    private final UserService service = UserService.INSTANCE;

    public MyRealm() {
    }

    // 授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SysUser user = service.findByUsername(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(service.findRoles(user));
        authorizationInfo.setStringPermissions(service.findPermissions(user));
        return authorizationInfo;
    }

    // 认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        SysUser user = service.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException();//没找到帐号
        }
        if (user.getType() >= 3) {
            throw new DisabledAccountException("前端用户和第三方用户不允许访问后台");
        }
        // 若用户有状态会影响是否允许登录，应该在此处判断
        // if(Boolean.TRUE.equals(user.getLocked())) {
        //     throw new LockedAccountException(); //帐号锁定
        // }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以在此判断或自定义实现
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getUsername() + user.getSalt()));
        return info;
    }
}
