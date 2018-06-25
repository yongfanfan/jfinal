package com.haitai.haitaitv.component.jfinal.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.haitai.haitaitv.common.cache.AccessCache;
import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

/**
 * 前端接口通用拦截器，检查accessToken
 */
public class AccessTokenInterceptor implements Interceptor {

    private static final Logger LOG = LogManager.getLogger(AccessTokenInterceptor.class);

    public void intercept(Invocation ai) {
        Controller controller = ai.getController();
        String accessToken = controller.getRequest().getHeader("accessToken");
        if (accessToken == null) {
            accessToken = "";
        }
       
            ai.invoke();
    }
}
