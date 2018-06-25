package com.haitai.haitaitv.component.constant;

/**
 * session或request中的通用变量名
 *
 * @author liuzhou
 *         create at 2017-03-29 23:35
 */
public class SessionConsts {

    /**
     * 登录用户
     */
    public static final String USER = "SESSION_USER";
    // 以下变量是设置到request的
    /**
     * 项目根路径，末尾带/
     */
    public static final String BASE_PATH = "BASE_PATH";
    public static final String HEAD_TITLE = "HEAD_TITLE";
    public static final String HEAD_KEYWORDS = "HEAD_KEYWORDS";
    public static final String HEAD_DESCRIPTION = "HEAD_DESCRIPTION";
    public static final String STB_USER = "STB_USER";

    private SessionConsts() {
    }

}
