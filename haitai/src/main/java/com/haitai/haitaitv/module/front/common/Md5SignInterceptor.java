package com.haitai.haitaitv.module.front.common;

import com.alibaba.fastjson.JSONObject;
import com.haitai.haitaitv.common.cache.HtvCache;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.haitai.haitaitv.component.util.encryption.MD5Util;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 设置tetle,keywordes,description至session，由于只有完整页面需要这些属性，所以不必所有action都用它拦截
 */
public class Md5SignInterceptor implements Interceptor {

    public static void main(String[] args) {
        String src = "headImgUrl=https://mrht.huo.so/wechatHead/vi_32/zxqib90MVVzAob86jh4873hqiacsWSnVBp6E2xorDcD2LCQPwTMvjSXeQibqT7DubfCcuWNJEeMtUleUk9iaOoTZ5A/0&nickname=睡不醒的呼噜虫&openId=3935228142039168&operaid=550000&thirdid=oPmAdwIuB4XrLPzKKWyEZvTEZeaU&uuid=9d90b350-a9df-4b12-ba95-17e5fcfd2f5c";
        String sign = MD5Util.encrypt(src + "&key=8d0d5072115546b8ba1adc21df88679d");
        System.out.println(sign);
    }

    public void intercept(Invocation ai) {
        Controller controller = ai.getController();
        HttpServletRequest request = controller.getRequest();
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<String> keyValList = new ArrayList<String>();
        parameterMap.forEach((k, v) -> {
            if (!(v == null || v.length == 0 || StringUtils.isBlank(v[0]) || k.equals("sign"))) {
                keyValList.add(k + "=" + v[0]);
            }
        });
        keyValList.sort((a, b) -> a.compareTo(b));
        String src = StringUtils.join(keyValList, "&");
        String sign = MD5Util.encrypt(src + "&key=8d0d5072115546b8ba1adc21df88679d");
        if (sign.equalsIgnoreCase(request.getParameter("sign"))) {
            ai.invoke();
        } else {
            JSONObject json = new JSONObject();
            json.put("status", 0);// 成功
            json.put("message", "签名错误");
            controller.renderJson(json);
        }
    }
}
