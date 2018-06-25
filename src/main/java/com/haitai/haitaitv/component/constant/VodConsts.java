package com.haitai.haitaitv.component.constant;

import com.jfinal.kit.PropKit;

/**
 * vod相关的常量
 *
 * @author liuzhou
 *         create at 2017-05-04 16:23
 */
public class VodConsts {

    public static final boolean VOD_ENABLE = PropKit.getBoolean("VOD.ENABLE", false);
    public static final String VOD_URL = PropKit.get("VOD.URL");
    public static final String VOD_PORTAL_ID = PropKit.get("VOD.PORTAL_ID");

    private VodConsts() {
    }
}
