package com.haitai.haitaitv.component.constant;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;


public class JmsConsts {
    private static final Prop PROP = PropKit.use("conf/config.properties");
    //是否启用jms
    public static final boolean JMS_ENABLED = PROP.getBoolean("JMS_ENABLED", true);
    public static final String NEIWANG_MQ_URL = PROP.get("NEIWANG_MQ_URL", "failover:tcp://192.168.1.139:61616");
    //?jms.blobTransferPolicy.defaultUploadUrl=http://localhost:8181/fileserver/    http://localhost:8080/uploads/
    public static final String NEIWANG_MQ_USER = PROP.get("NEIWANG_MQ_USER", "admin");
    public static final String NEIWANG_MQ_PASSWORD = PROP.get("NEIWANG_MQ_PASSWORD", "admin");
    public static final String NEIWANG_MQ_KEY = PROP.get("NEIWANG_MQ_KEY", "8d0d5072115546b8ba1adc21df88679d");
    public static final Integer DEFAULT_HANDLE_THREAD_POOL = PROP.getInt("DEFAULT_HANDLE_THREAD_POOL", 50);
    
    public static final String JMAKE_TO_PRIVATE_DBTABLE = "jmake.to.private.dbtable";
    public static final String JMAKE_TO_PUBLIC_DBTABLE = "jmake.to.public.dbtable";
    public static final String JMAKE_ENTITY_PACKAGE = "com.jmaker.jmakercms.common.entity";
    
    
}
