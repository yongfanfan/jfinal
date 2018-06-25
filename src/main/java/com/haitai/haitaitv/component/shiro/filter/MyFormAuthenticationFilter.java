package com.haitai.haitaitv.component.shiro.filter;

import com.haitai.haitaitv.common.entity.SysLog;
import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.repository.SysLogDao;
import com.haitai.haitaitv.module.back.system.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.time.LocalDateTime;

/**
 * 登录校验
 *
 * @author liuzhou
 *         create at 2016-12-06 14:29
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        // 当验证码验证失败时不再走身份认证拦截器
        return request.getAttribute(getFailureKeyAttribute()) != null
                || super.onAccessDenied(request, response, mappedValue);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        // 记录日志
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SysUser user = UserService.INSTANCE.findByUsername(username);
        SQLManager sm = JFinalBeetlSql.dao();
        String tableName = sm.getNc().getTableName(user.getClass());
        Integer updateId = user.getId();
        SysLog log = new SysLog(null, SysLog.TYPE_SYSTEM, SysLog.getTableRemark(tableName), tableName, updateId,
                SysLog.SYSTEM_LOGIN, "", LocalDateTime.now(), updateId);
        sm.getMapper(SysLogDao.class).insert(log);
        // 登录成功后直接跳转successUrl，而不跳转保存的url
        WebUtils.issueRedirect(request, response, getSuccessUrl());
        return false;
    }
}
