package com.haitai.haitaitv.component.jfinal.annotation;

import com.haitai.haitaitv.component.jfinal.interceptor.FrontExceptionInterceptor;
import com.haitai.haitaitv.component.jfinal.interceptor.AccessTokenInterceptor;
import com.haitai.haitaitv.module.front.common.BaseFrontController;

/**
 * 前端路由
 *
 * @author liuzhou
 *         create at 2017-05-04 15:06
 */
public class FrontRoutes extends AutoBindRoutes<BaseFrontController> {

    public FrontRoutes() {
        setBaseClass(BaseFrontController.class);
        setPkg("com/haitai/haitaitv/module/front");
    }

    @Override
    public void config() {
        addInterceptor(new AccessTokenInterceptor());
        addInterceptor(new FrontExceptionInterceptor());
        autoAdd();
    }
}
