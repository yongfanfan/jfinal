package com.haitai.haitaitv.component.jfinal.interceptor;

import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.OtherConsts;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.ActionException;
import com.jfinal.core.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 后端控制器通用异常拦截器
 */
public class BackExceptionInterceptor implements Interceptor {

    private final static Logger LOG = LogManager.getLogger(BackExceptionInterceptor.class);

    public void intercept(Invocation ai) {
        try {
            ai.invoke();
        } catch (Exception e) {
            if (e instanceof ActionException) {
                // 此类异常由jfinal处理
                throw e;
            }
            LOG.error("异常：", e);
            Controller controller = ai.getController();
            // 开发模式下才在页面上展示异常信息
            if (ConfigConsts.DEV_MODE) {
                controller.setAttr("error", e.toString());
            } else {
                controller.setAttr("error", "");
            }
            controller.render(OtherConsts.PAGES_500);
        }
    }
}
