package com.haitai.haitaitv.component.jfinal.annotation;

import com.haitai.haitaitv.component.jfinal.interceptor.BackExceptionInterceptor;
import com.haitai.haitaitv.component.shiro.ext.ShiroInterceptor;
import com.haitai.haitaitv.module.back.common.BaseBackController;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;

/**
 * 后端路由
 *
 * @author liuzhou
 *         create at 2017-05-04 15:06
 */
public class BackRoutes extends AutoBindRoutes<BaseBackController> {

    public BackRoutes() {
        setBaseClass(BaseBackController.class);
        setPkg("com/haitai/haitaitv/module/back");
    }

    @Override
    public void config() {
        // 设置后端视图根路径
        setBaseViewPath("/page");
        // session model转换
        addInterceptor(new SessionInViewInterceptor());
        // 权限拦截
        addInterceptor(new ShiroInterceptor());
        addInterceptor(new BackExceptionInterceptor());
        autoAdd();
    }
}
