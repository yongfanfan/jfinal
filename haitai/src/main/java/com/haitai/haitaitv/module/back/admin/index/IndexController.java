package com.haitai.haitaitv.module.back.admin.index;

import com.haitai.haitaitv.common.entity.SysLog;
import com.haitai.haitaitv.common.entity.SysUser;
import com.haitai.haitaitv.common.repository.SysLogDao;
import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.jfinal.annotation.ControllerBind;
import com.haitai.haitaitv.component.jfinal.interceptor.WholePageInterceptor;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import com.haitai.haitaitv.module.back.system.user.UserService;
import com.jfinal.aop.Before;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author liuzhou
 *         create at 2017-03-29 23:27
 */
@ControllerBind(controllerKey = "/admin")
@Before(WholePageInterceptor.class)
public class IndexController extends BaseBackController {

    public static final String LOGIN_PAGE = "/page/admin/index/login.html";
    public static final String INDEX_PAGE = "/page/admin/index/index.html";

    public void index() {
        SysUser user = getSessionUser();
        if (user == null) {
            setAttr("version", ConfigConsts.PROJECT_VERSION);
            redirect(LOGIN_PAGE);
            return;
        }
        // 其实页面上也可以直接使用SESSION_USER，但变量名太长
        setAttr("user", user);
        // 设置菜单
        setAttr("menu", UserService.INSTANCE.getMenuMap(user));
        setAttr("version", ConfigConsts.PROJECT_VERSION);
        render(INDEX_PAGE);
    }

    public void login() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            // get请求进login页，post请求进index页
            // （已登录状态下，在login页提交表单登录，实际上shiro的拦截器会直接放行而不处理，所以会进入此处）
            if ("GET".equalsIgnoreCase(getRequest().getMethod())) {
                setAttr("version", ConfigConsts.PROJECT_VERSION);
                render(LOGIN_PAGE);
            } else {
                index();
            }
            return;
        } else if (isAjaxRequest(getRequest())) {
            // 如果是异步请求，则返回json（异步请求资源，但session过期被shiro拦截后就会重定向到这里）
            dwzTimeout();
            return;
        }
        String exceptionClassName = (String) getRequest().getAttribute("shiroLoginFailure");
        String error;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if ("imageCodeFailure".equals(exceptionClassName)) {
            error = "验证码错误！";
        } else if (exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        } else {
            error = "请登录";
        }
        setAttr("msg", error);
        setAttr("version", ConfigConsts.PROJECT_VERSION);
        render(LOGIN_PAGE);
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public void logout() {
        SysUser user = getSessionUser();
        if (user != null) {
            // 添加日志
            String tableName = sm.getNc().getTableName(user.getClass());
            Integer updateId = user.getId();
            SysLog log = new SysLog(null, SysLog.TYPE_SYSTEM, SysLog.getTableRemark(tableName), tableName, updateId,
                    SysLog.SYSTEM_LOGOUT, "", LocalDateTime.now(), updateId);
            sm.getMapper(SysLogDao.class).insert(log);
        }
        SecurityUtils.getSubject().logout();

        setAttr("msg", "您已退出");
        setAttr("version", ConfigConsts.PROJECT_VERSION);
        render(LOGIN_PAGE);
    }

}
