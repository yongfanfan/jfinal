package com.haitai.haitaitv.component.constant.operator;

import com.haitai.haitaitv.component.constant.ConfigConsts;

/**
 * 未来电视相关的常量
 *
 * @author liuzhou
 *         create at 2017-04-16 19:12
 */
public class IcntvConsts {

    public static final String CP_CODE = "YHKJ_SH";
    public static final String KEY = "ac09ac1dfbefc40eff9ddd4b6caba9bf";
    // public static final String CIS_ADDR = "123.206.7.49";// 123.206.7.49:8080为未来的测试地址
    // public static final String PORT = "8080";
    public static final String CIS_ADDR = "123.206.1.226";// 123.206.1.226:80为未来的正式地址
    public static final String PORT = "80";
    public static final String OPERATOR_ID = "660001";
    public static final boolean FLAG = OPERATOR_ID.equals(ConfigConsts.COMMERCE_OPERATOR_ID);

    private IcntvConsts() {
    }
}
