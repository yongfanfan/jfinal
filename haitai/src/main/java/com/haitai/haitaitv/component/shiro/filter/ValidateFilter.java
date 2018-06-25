package com.haitai.haitaitv.component.shiro.filter;

import com.haitai.haitaitv.component.util.StrUtil;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 验证码校验
 *
 * @author liuzhou
 *         create at 2016-12-06 14:17
 */
public class ValidateFilter extends AccessControlFilter {

    // 是否开启验证码支持
    private boolean imageCodeEnabled = true;
    // 前台提交的验证码参数名
    private String imageCodeParam = "imageCode";
    // 验证失败后存储到的属性名
    private String failureKeyAttribute = "shiroLoginFailure";

    public void setImageCodeEbabled(boolean imageCodeEnabled) {
        this.imageCodeEnabled = imageCodeEnabled;
    }

    public void setImageCodeParam(String imageCodeParam) {
        this.imageCodeParam = imageCodeParam;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
        request.setAttribute("imageCodeEnabled", imageCodeEnabled);

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        //2、判断验证码是否禁用或不是表单提交（允许访问）
        if (!imageCodeEnabled || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }
        //3、此时是表单提交，验证验证码是否正确
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        String imageCode = session != null ? (String) session.getAttribute(ImageCodeFilter.class.getName()) : null;
        String checkCode = httpServletRequest.getParameter(imageCodeParam);

        return StrUtil.isNotEmpty(imageCode) && imageCode.equalsIgnoreCase(checkCode);
    }

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //如果验证码失败了，存储失败key属性
        request.setAttribute(failureKeyAttribute, "imageCodeFailure");
        return true;
    }
}
