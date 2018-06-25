package com.haitai.haitaitv.component.jfinal.base;

import com.alibaba.fastjson.JSON;
import com.haitai.haitaitv.component.constant.SessionConsts;
import com.haitai.haitaitv.component.jfinal.ext.AbstractController;
import com.haitai.haitaitv.component.util.encryption.DES3Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 封装所有控制器的通用属性与操作
 *
 * @author liuzhou
 *         create at 2017-03-26 20:30
 */
public abstract class BaseController extends AbstractController {

    protected static final Logger LOG = LogManager.getLogger(BaseController.class);
    protected static final DES3Utils MQ_MSG_DES = DES3Utils.INSTANCE;
    protected static SQLManager sm;

    public static void init() {
        sm = JFinalBeetlSql.dao();
    }

    /**
     * 与getPara(String name)的区别在于，若为空串，会返回null
     */
    public String getParaNoEmptyString(String name) {
        String result = getPara(name);
        return "".equals(result) ? null : result;
    }

    protected String produceUuid() {
        return UUID.randomUUID().toString();
    }

    protected String getNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    protected String getBasePath() {
        return (String) getRequest().getAttribute(SessionConsts.BASE_PATH);
    }

    /**
     * html5移除了对<meta http-equiv="cache-control" content="no-cache" />等的支持，应该在header中设置
     */
    protected void setHeaderNoCache() {
        HttpServletResponse response = getResponse();
        response.setHeader("Pragma", "no-cache");
        response.addHeader("Cache-Control", "must-revalidate");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
    }

    /**
     * 此方法将以"yyyy-MM-dd HH:mm:ss"模式来格式化对象中的时间类型，然后再renderJson
     */
    public void renderJsonWithDateFormat(Object object) {
        renderJsonWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 此方法将以dateFormat模式来格式化对象中的时间类型，然后再renderJson
     */
    public void renderJsonWithDateFormat(Object object, String dateFormat) {
        String jsonString = JSON.toJSONStringWithDateFormat(object, dateFormat);
        renderJson(jsonString);
    }
}
