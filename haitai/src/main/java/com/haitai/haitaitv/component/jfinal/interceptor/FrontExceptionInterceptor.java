package com.haitai.haitaitv.component.jfinal.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.haitai.haitaitv.module.front.common.ResponsiveInstance;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 前端控制器通用异常拦截器
 *
 * @author liuzhou
 *         create at 2017-06-20 16:16
 */
public class FrontExceptionInterceptor implements Interceptor {

    private final static Logger LOG = LogManager.getLogger(FrontExceptionInterceptor.class);

    public void intercept(Invocation ai) {
        try {
            // LocalDateTime start = LocalDateTime.now();
            ai.invoke();
            /*LocalDateTime end = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yymmdd HH:MM:ss");
            LOG.error("接口:{},开始时间:{},结束时间:{}", ai.getActionKey(), formatter.format(start),
                    formatter.format(end));*/
        } catch (Exception e) {
            LOG.error("异常：", e);
            Controller controller = ai.getController();
            controller.renderJson(ResponsiveInstance.EXCEPTION); // 服务器内部错误
        }
    }
}
