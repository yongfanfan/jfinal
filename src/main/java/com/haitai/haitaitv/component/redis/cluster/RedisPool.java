package com.haitai.haitaitv.component.redis.cluster;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

public class RedisPool {
    
    private int defaultDatabase;
    private JedisCluster cluster;
    private JedisSentinelPool jedisSentinelPool;
    private static final String name = "master";
    private static final ConcurrentHashMap<String, JedisCluster> JedisClusterMap = new ConcurrentHashMap<String, JedisCluster>();
    private final Map<Integer, JedisSentinelPool> sentinelPool = new ConcurrentHashMap<Integer, JedisSentinelPool>();
    
    /**
     * 获取JedisSentinelPool实例
     *
     * @param password          密码
     * @param connectionTimeout 连接超时时间
     * @param soTimeout         读取超时时间
     * @param maxTotal          可用连接实例的最大数目，默认值为8；
     *                          如果赋值为-1，则表示不限制；
     *                          如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
     * @param maxIdle           控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值是8
     * @param maxWaitMillis     等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
     * @param testOnBorrow      在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
     * @param sentinelNodes     哨兵模式节点连接地址以及端口号
     * @param masterName        主节点名
     * @return JedisSentinelPool
     */
    public RedisPool(String password, int maxTotal, int maxIdle, int maxWaitMillis,
                     int connectionTimeout, int soTimeout, boolean testOnBorrow, int defaultDatabase,
                     Set<String> sentinelNodes, String masterName) {
        this.defaultDatabase = defaultDatabase;
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        this.jedisSentinelPool = new JedisSentinelPool(masterName, sentinelNodes, config, connectionTimeout, soTimeout, password,
                defaultDatabase, null);
        sentinelPool.put(defaultDatabase, jedisSentinelPool);
    }

    /**
     * 获取JedisCluster实例
     *
     * @param password          密码
     * @param connectionTimeout 连接超时时间
     * @param soTimeout         读取超时时间
     * @param maxTotal          可用连接实例的最大数目，默认值为8；
     *                          如果赋值为-1，则表示不限制；
     *                          如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
     * @param maxIdle           控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值是8
     * @param maxWaitMillis     等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
     * @param testOnBorrow      在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的
     * @return JedisCluster
     */
    public RedisPool(Set<HostAndPort> jedisClusterNode, String password, int maxTotal, int maxIdle, int maxWaitMillis,
            int connectionTimeout, int soTimeout, boolean testOnBorrow, int maxAttempts) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        config.setTestOnBorrow(testOnBorrow);
        this.cluster = new JedisCluster(jedisClusterNode, connectionTimeout, soTimeout, maxAttempts, password, config);
        JedisClusterMap.put(name, cluster);
    }
    
    /**
     * 哨兵模式
     * 
     * 获得一个Jedis
     *
     * @return Jedis
     */
    public Jedis getJedisSentinelPool() {
        return getJedisSentinelPool(defaultDatabase);
    }
    
    public Jedis getJedisSentinelPool(int defaultDatabase) {
        return sentinelPool.get(defaultDatabase).getResource();
    }
    
    /**
     * 集群模式
     * 
     * 获得一个JedisCluster
     *
     * @return JedisCluster
     */
    public JedisCluster getJedisCluster() {
        return getJedisCluster(name);
    }
    
    public JedisCluster getJedisCluster(String name) {
        return JedisClusterMap.get(name);
    }
    
    

}
