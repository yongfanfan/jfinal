package com.haitai.haitaitv.component.jfinal.interceptor;

/**
 * 对serverType为0的进行拦截
 *
 * @author liuzhou
 *         create at 2017-04-19 13:52
 */
public class ServerType0Interceptor extends ServerTypeInterceptor {

    @Override
    protected int getServerType() {
        return 0;
    }

}
