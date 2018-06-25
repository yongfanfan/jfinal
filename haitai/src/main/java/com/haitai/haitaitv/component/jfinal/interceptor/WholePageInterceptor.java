package com.haitai.haitaitv.component.jfinal.interceptor;

import com.haitai.haitaitv.common.cache.HtvCache;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * 设置tetle,keywordes,description至session，由于只有完整页面需要这些属性，所以不必所有action都用它拦截
 */
public class WholePageInterceptor implements Interceptor {

    public void intercept(Invocation ai) {
        Controller controller = ai.getController();
        HttpServletRequest request = controller.getRequest();
        request.setAttribute(SessionConsts.HEAD_TITLE, HtvCache.getHeadTitle());
        request.setAttribute(SessionConsts.HEAD_KEYWORDS, HtvCache.getHeadTitle());
        request.setAttribute(SessionConsts.HEAD_DESCRIPTION, HtvCache.getHeadTitle());
        ai.invoke();
    }

}
