package com.haitai.haitaitv.component.redis.sentinel;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.haitai.haitaitv.component.redis.cluster.RedisPool;
import com.jfinal.kit.Prop;

public class RedisSentinel {
    private final static int DEFAULT_MAX_TOTAL = 1024;
    private final static int DEFAULT_MAX_IDLE = 200;
    private final static int DEFAULT_MAX_WAIT_MILLIS = 10000;
    private final static int DEFAULT_TIMEOUT = 10000;
    private final static boolean DEFAULT_TEST_ON_BORROW = true;
    private final static String DEFAULT_MASTER_NAME = "MyMaster";
    private final static int DEFAULT_DATABASE = 15;
    
    public static RedisPool start(Prop prop) {
        String password = prop.get("password");
        int maxTotal = prop.getInt("maxTotal", DEFAULT_MAX_TOTAL);
        int maxIdle = prop.getInt("maxIdle", DEFAULT_MAX_IDLE);
        int maxWaitMillis = prop.getInt("maxWaitMillis", DEFAULT_MAX_WAIT_MILLIS);
        int connectionTimeout = prop.getInt("connectionTimeout", DEFAULT_TIMEOUT);
        int soTimeout = prop.getInt("soTimeout", DEFAULT_TIMEOUT);
        boolean testOnBorrow = prop.getBoolean("testOnBorrow", DEFAULT_TEST_ON_BORROW);
        String sentinelNodes = prop.get("sentinelNodes");
        // 哨兵模式
        String masterName = prop.get("masterName", DEFAULT_MASTER_NAME);
        int defaultDatabase = prop.getInt("database", DEFAULT_DATABASE);
        //127:0.0.1:7300,127.0.0.1:7400,127.0.0.1:7500
        Set<String> sentinels = Stream.of(sentinelNodes.split(",")).collect(Collectors.toSet());
        return new RedisPool( password, maxTotal, maxIdle, maxWaitMillis, connectionTimeout, soTimeout,
                testOnBorrow, defaultDatabase, sentinels, masterName);
    }
}
