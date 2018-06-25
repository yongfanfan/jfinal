package com.haitai.haitaitv.component.jfinal.interceptor;

import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 根据serverType进行拦截
 *
 * @author liuzhou
 *         create at 2017-04-19 13:45
 */
public abstract class ServerTypeInterceptor implements Interceptor {

    protected abstract int getServerType();

    @Override
    public void intercept(Invocation inv) {
        if (ConfigConsts.SERVER_TYPE == getServerType()) {
            inv.getController().renderError(403);
            return;
        }
        inv.invoke();
    }

}
