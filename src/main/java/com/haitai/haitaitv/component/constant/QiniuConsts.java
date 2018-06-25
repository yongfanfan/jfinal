package com.haitai.haitaitv.component.constant;

import com.jfinal.kit.PropKit;

/**
 * 七牛相关的常量
 *
 * @author liuzhou
 *         create at 2017-04-02 16:56
 */
public class QiniuConsts {

    public static final boolean QINIU_ENABLED = PropKit.getBoolean("QINIU.ENABLED", false);
    public static final String QINIU_HOST = PropKit.get("QINIU.HOST");
    public static final String QINIU_ACCESS_KEY = PropKit.get("QINIU.ACCESS.KEY");
    public static final String QINIU_ACCESS_SECRET = PropKit.get("QINIU.ACCESS.SECRET");

    private QiniuConsts() {
    }
}
