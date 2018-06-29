package com.haitai.haitaitv.component.redis.cluster;

import com.jfinal.kit.Prop;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/**
 * jFinal的redis插件不支持redis集群模式，我自己扩展一个吧
 *
 */
public class RedisCluster {
    
    private final static int DEFAULT_MAX_TOTAL = 1024;
    private final static int DEFAULT_MAX_IDLE = 200;
    private final static int DEFAULT_MAX_WAIT_MILLIS = 10000;
    private final static int DEFAULT_TIMEOUT = 10000;
    private final static boolean DEFAULT_TEST_ON_BORROW = true;
    private final static int MAX_ATTEMPTS = 5;
    
    public static RedisPool start(Prop prop) {
        String nodes = prop.get("nodes", "127.0.0.1:9300");
        String password = prop.get("password");
        int maxTotal = prop.getInt("maxTotal", DEFAULT_MAX_TOTAL);
        int maxIdle = prop.getInt("maxIdle", DEFAULT_MAX_IDLE);
        int maxWaitMillis = prop.getInt("maxWaitMillis", DEFAULT_MAX_WAIT_MILLIS);
        int connectionTimeout = prop.getInt("connectionTimeout", DEFAULT_TIMEOUT);
        int soTimeout = prop.getInt("soTimeout", DEFAULT_TIMEOUT);
        boolean testOnBorrow = prop.getBoolean("testOnBorrow", DEFAULT_TEST_ON_BORROW);
        int maxAttempts = prop.getInt("maxAttempts", MAX_ATTEMPTS);
        Set<HostAndPort> jedisClusterNode = Stream.of(nodes.split(","))
                .map(s -> {
                    String[] a = s.split(":");
                    return new HostAndPort(a[0], Integer.valueOf(a[1]));
                }).collect(Collectors.toSet());
        return new RedisPool(jedisClusterNode, password, maxTotal, maxIdle, maxWaitMillis, connectionTimeout, soTimeout, testOnBorrow, maxAttempts);
    }
    
}