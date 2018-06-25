package com.haitai.haitaitv.component.constant.operator;

import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.VodConsts;
import com.jfinal.kit.PropKit;

/**
 * 上海电信iptv相关的常量
 *
 * @author liuzhou
 *         create at 2017-05-04 10:26
 */
public class ShanghaiIptvConsts {

    public static final String OPERATOR_ID = "200000";
    public static final boolean FLAG = OPERATOR_ID.equals(ConfigConsts.COMMERCE_OPERATOR_ID);
    public static final String MEDIA_CONTENT_INTERFACE_NAME = "V2P/Media/Content";
    public static final String INTERFACE_SERVER_IP = VodConsts.VOD_URL;
    public static final String AUTHENTICATION_INTERFACE = PropKit.get("VOD.INTERFACE");

    private ShanghaiIptvConsts() {
    }
}
