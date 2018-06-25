package com.haitai.haitaitv.component.jfinal.handler;

import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 不允许直接访问某些静态资源
 *
 * @author liuzhou
 *         create at 2017-06-06 14:10
 */
public class StaticResourceHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        if (target.startsWith("/component/swagger")) {
            if (target.endsWith("index.html") || target.endsWith("swagger.json")) {
                HandlerKit.renderError404(request, response, isHandled);
            }
        }
        next.handle(target, request, response, isHandled);
    }
}
