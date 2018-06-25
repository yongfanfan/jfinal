package com.haitai.haitaitv.component.jfinal.handler;

import com.haitai.haitaitv.component.constant.operator.HzdtvConsts;
import com.haitai.haitaitv.component.constant.operator.ShanghaiIptvConsts;
import com.haitai.haitaitv.component.util.StrUtil;
import com.jfinal.handler.Handler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by liuzhou on 2017/3/5.
 */
public class HtmlHandler extends Handler {

    private static final Logger LOG = LogManager.getLogger(HtmlHandler.class);

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        // target只是请求路径，不包含项目路径(如项目放在tomcat的webapp/abc/def下，项目路径即/abc/def)和请求参数(即?之后的部分)
        if (target.equals("/web/index.html")) {
            String queryString = request.getQueryString();
            if (StrUtil.isEmpty(queryString) || !queryString.contains("userId")) {
                if (ShanghaiIptvConsts.FLAG) {
                    handleIptv(target, request, response);
                    return;
                }
                if (HzdtvConsts.FLAG) {
                    handleHzdtv(target, request, response);
                    return;
                }
            }
            next.handle(target, request, response, isHandled);
            return;
        }

        if (target.equals("/web/page/index.html")) {
            String queryString = request.getQueryString();
            if (StrUtil.isNotEmpty(queryString) && queryString.contains("productId") && !queryString.contains("userId")) {
                if (ShanghaiIptvConsts.FLAG) {
                    handleIptv(target, request, response);
                    return;
                }
                if (HzdtvConsts.FLAG) {
                    handleHzdtv(target, request, response);
                    return;
                }
            }
            next.handle(target, request, response, isHandled);
            return;
        }

        /*由于shiro的user拦截器过滤成功后，会在跳转的链接后附;jsessionid=**，
        此时jfinal无法将login识别为method而是将login;jsessionid=**直接识别为请求参数，
        导致无法正确跳转登录页，下面的代码解决了此问题*/
        int index = target.indexOf("login;jsessionid=");
        if (index > 0) {
            target = target.substring(0, index) + "login";
        }
        next.handle(target, request, response, isHandled);
    }

    private void handleIptv(String target, HttpServletRequest request, HttpServletResponse response) {
        String queryString = request.getQueryString();
        // iptv服务器对/web/index.html并且是非get请求单独处理
        String inUrl = request.getParameter("inUrl");
        String userId = request.getParameter("UserID");
        String userToken = request.getParameter("UserToken");
        /*Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (userId != null && userToken != null) {
                    break;
                }
                String name = cookie.getName();
                if ("userId".equals(name)) {
                    userId = cookie.getValue();
                    continue;
                }
                if ("userToken".equals(name)) {
                    userToken = cookie.getValue();
                }
            }
        }*/
        // 若inUrl里带有userId和userToken，用inUrl里的
        if (inUrl == null) {
            inUrl = ""; // 防止异常
        }
        String[] arr = inUrl.split("[?&]");
        for (String s : arr) {
            if (s.startsWith("UerID=")) {
                userId = s.substring(6);
            } else if (s.startsWith("UserToken=")) {
                userToken = s.substring(10);
            }
        }

        String redirectUrl = request.getContextPath() + target;
        if (StrUtil.isNotEmpty(queryString)) {
            redirectUrl += "?" + queryString + "&userId=" + userId + "&userToken=" + userToken;
        } else {
            redirectUrl += "?userId=" + userId + "&userToken=" + userToken;
        }
        try {
            redirectUrl += "&inUrl=" + URLEncoder.encode(inUrl, "utf-8");
            //redirectUrl = request.getContextPath() + redirectUrl;
            LOG.info(redirectUrl);
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            LOG.error("iptv首页特殊处理发生异常", e);
        }
    }

    private void handleHzdtv(String target, HttpServletRequest request, HttpServletResponse response) {
        String queryString = request.getQueryString();
        /*System.out.println("HzdtvUtil");
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println("key: " + key + " , value: " + value);
            map.put(key, value);
        }*/
        String stbModel = request.getHeader("x-terminal-model");
        String stbId = request.getHeader("x-terminal-id");
        String userId = request.getHeader("x-user-id");
        String userToken = request.getHeader("x-userprofile");
        String regionId = request.getHeader("x-region-id");

        String redirectUrl = request.getContextPath() + target;
        if (StrUtil.isNotEmpty(queryString)) {
            redirectUrl += "?" + queryString + "&userId=" + userId;
        } else {
            redirectUrl += "?userId=" + userId;
        }
        redirectUrl += "&userToken" + userToken + "&stbModel=" + stbModel + "&stbId=" + stbId + "&regionId=" + regionId;
        try {
            //String redirectUrl = target.replace("/web/index.html", "/web/loading.html");
            LOG.info(redirectUrl);
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            LOG.error("hzdtv首页特殊处理发生异常", e);
        }
    }
}
