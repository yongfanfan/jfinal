package com.haitai.haitaitv.component.constant;

import com.jfinal.kit.PropKit;

/**
 * 系统配置相关的常量
 */
public class ConfigConsts {

    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = PropKit.getInt("public.default_page_size", 50);
    /**
     * 是否开发模式
     */
    public static final boolean DEV_MODE = PropKit.getBoolean("public.dev_mode", false);

    public static final int SERVER_TYPE = PropKit.getInt("SERVER.TYPE");

    public static final boolean SERVER_INDEPENDENT = PropKit.getBoolean("SERVER.INDEPENDENT", false);

    public static final String COMMERCE_OPERATOR_ID = PropKit.get("COMMERCE.OPERATOR_ID");

    public static final boolean SATA_NOT_CLEAR = PropKit.getBoolean("SATA.NOT_CLEAR", false);

    public static final boolean SATA_NOT_START = PropKit.getBoolean("SATA.NOT_START", false);

    public static final boolean CREATE_USER_NOTIFY = PropKit.getBoolean("CREATE_USER_NOTIFY", false);

    public static final String PROJECT_VERSION = PropKit.use("/conf/version.properties").get("PROJECT.VERSION");
    
    public static final Long MAX_LENGTH = PropKit.getLong("MAX_LENGTH");

    private ConfigConsts() {
    }

}
