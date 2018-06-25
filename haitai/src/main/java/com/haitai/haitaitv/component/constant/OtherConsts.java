package com.haitai.haitaitv.component.constant;

import com.haitai.haitaitv.component.util.StrUtil;
import com.jfinal.kit.PropKit;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 其他常量
 *
 * @author liuzhou
 *         create at 2017-03-30 15:27
 */
public class OtherConsts {

    public static final String PAGES_401 = PropKit.get("PAGES.401");
    public static final String PAGES_403 = PropKit.get("PAGES.403");
    public static final String PAGES_404 = PropKit.get("PAGES.404");
    public static final String PAGES_500 = PropKit.get("PAGES.500");
    public static final String SEND_COUPON_URL = PropKit.get("SEND_COUPON_URL");
    public static final boolean ENABLE_EHCACHE_JMX =
            PropKit.getBoolean("public.enable_ehcache_jmx", false);
    public static final boolean NO_SHELL_UTIL = PropKit.getBoolean("NO_SHELL_UTIL", false);
    public static final String DEFAULT_HEAD_IMAGE = PropKit.get("DEFAULT_HEAD_IMAGE");
    public static final String ZIP_PASSWORD = PropKit.get("ZIP.PASSWORD");
    public static final String WX_LOGIN_BASE_URL = PropKit.get("WX_LOGIN_BASE_URL");
    public static final String ES_IP_ADDRESSES = PropKit.get("ES_IP_ADDRESSES");
    public static final String ES_CLUSTER_NAME = PropKit.get("ES_CLUSTER_NAME");
    public static final boolean ES_DISABLE = StrUtil.isEmpty(ES_IP_ADDRESSES);
    /**
     * 后台默认密码
     */
    public static final String DEFAULT_PASSWORD = new Md5Hash("1717888").toString();

    private OtherConsts() {
    }

}
