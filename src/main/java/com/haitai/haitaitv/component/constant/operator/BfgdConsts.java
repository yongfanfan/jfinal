package com.haitai.haitaitv.component.constant.operator;

import com.haitai.haitaitv.component.constant.ConfigConsts;
import com.haitai.haitaitv.component.constant.MqConsts;
import com.jfinal.kit.PropKit;

/**
 * 北方广电iptv相关的常量
 *
 * @author liuzhou
 *         create at 2017-08-12 12:33
 */
public class BfgdConsts {

    public static final String OPERATOR_ID = "110001";
    public static final boolean FLAG = OPERATOR_ID.equals(ConfigConsts.COMMERCE_OPERATOR_ID);
    public static final String FTP_PATH = PropKit.get("FTP_PATH");
    public static final String WSDL_PATH = MqConsts.NEIWANG_SERVER_STATIC + "/wsdl/";
    public static final String WSDL_PUBLISH_URL = WSDL_PATH + "WebPublish.wsdl";
    public static final String SUBJECT_ID = "10000100000000090000000000003725";

    private BfgdConsts() {
    }
}
