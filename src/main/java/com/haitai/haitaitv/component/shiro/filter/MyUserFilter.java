package com.haitai.haitaitv.component.shiro.filter;

import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.haitai.haitaitv.module.back.system.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author liuzhou
 *         create at 2016-11-25 15:40
 */
public class MyUserFilter extends UserFilter {

    /**
     * super.onPreHandle()是UserFilter的主要逻辑，若用户未认证将返回false，否则返回true
     * 此方法的返回值表示是否执行下一个拦截器
     */
    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return super.onPreHandle(request, response, mappedValue) && setSession();
    }

    /**
     * 此方法可用于往session注入一些通用属性
     */
    private boolean setSession() {
        Session session = SecurityUtils.getSubject().getSession();
        if (session.getAttribute(SessionConsts.USER) != null) {
            return true;
        }
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        UserService service = UserService.INSTANCE;
        SysUser user = service.findByUsername(username);
        if (user == null) {
            // 此处不可能执行，因为super.onPreHandle()为true才会执行setSession()方法，那么用户应该是存在的
            return false;
        }
        // 查出并存入user的operatorId值
        user.setOperatorId(service.getOperatorId(user));
        session.setAttribute(SessionConsts.USER, user);
        return true;
    }

}
