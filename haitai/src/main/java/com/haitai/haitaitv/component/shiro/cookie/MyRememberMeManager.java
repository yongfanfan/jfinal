package com.haitai.haitaitv.component.shiro.cookie;

import com.haitai.haitaitv.common.entity.SysLog;
import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.repository.SysLogDao;
import com.haitai.haitaitv.module.back.system.user.UserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.servlet.Cookie;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

import java.time.LocalDateTime;


/**
 * 实现"记住我"功能
 *
 * @author liuzhou
 *         create at 2016-12-08 11:32
 */
public class MyRememberMeManager extends CookieRememberMeManager {

    private final UserService service = UserService.INSTANCE;
    // cookie有效时长，秒
    private int maxAge = Cookie.ONE_YEAR;

    public MyRememberMeManager() {
        getCookie().setMaxAge(maxAge);
        // 密钥，由CreateAesKey类生成
        setCipherKey(Base64.decode("NsZXjXVklWPZwOfkvk6kUA=="));
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
        getCookie().setMaxAge(maxAge);
    }

    // 重写写入cookie的内容
    @Override
    public void rememberIdentity(Subject subject, AuthenticationToken token, AuthenticationInfo authcInfo) {
        String username = (String) token.getPrincipal();
        SysUser user = service.findByUsername(username);
        if (user == null) {
            return;
        }
        // 格式：username:password:expire
        long expire = System.currentTimeMillis() + maxAge * 1000;
        String content = user.getUsername() + ":" + user.getPassword() + ":" + expire;
        byte[] bytes = content.getBytes();
        bytes = encrypt(bytes);
        rememberSerializedIdentity(subject, bytes);
    }

    // 重写从cookie中读取内容
    @Override
    protected PrincipalCollection convertBytesToPrincipals(byte[] bytes, SubjectContext subjectContext) {
        bytes = decrypt(bytes);
        String content[] = new String(bytes).split(":");
        if (content.length != 3) {
            return null;
        }
        long expire = Long.parseLong(content[2]);
        if (expire < System.currentTimeMillis()) {
            // cookie中的expire值早于系统当前毫秒值，表示cookie过期
            return null;
        }
        String username = content[0];
        String password = content[1];
        SysUser user = service.findByUsernameAndPassword(username, password);
        if (user == null) {
            // cookie中信息无用的情况下，清除掉这个cookie
            forgetIdentity(subjectContext);
            return null;
        }
        // 记录日志
        SQLManager sm = JFinalBeetlSql.dao();
        String tableName = sm.getNc().getTableName(user.getClass());
        Integer updateId = user.getId();
        SysLog log = new SysLog(null, SysLog.TYPE_SYSTEM, SysLog.getTableRemark(tableName), tableName, updateId,
                SysLog.SYSTEM_LOGIN, "", LocalDateTime.now(), updateId);
        sm.getMapper(SysLogDao.class).insert(log);
        // 暂不清楚realmName是否影响功能
        return new SimplePrincipalCollection(username, "MyRememberMeManager");
    }
}
