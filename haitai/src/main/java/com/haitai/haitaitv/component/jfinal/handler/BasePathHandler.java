package com.haitai.haitaitv.component.jfinal.handler;

import com.haitai.haitaitv.component.constant.SessionConsts;
import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuzhou on 2017/3/5.
 */
public class BasePathHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        // getContextPath的开头有/，结尾没有/
        String path = request.getContextPath() + "/";
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
        request.setAttribute(SessionConsts.BASE_PATH, basePath);
        next.handle(target, request, response, isHandled);
    }
}
