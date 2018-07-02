package com.haitai.haitaitv.component.redis;


import com.haitai.haitaitv.component.redis.cluster.RedisCluster;
import com.haitai.haitaitv.component.redis.cluster.RedisPool;
import com.haitai.haitaitv.component.redis.sentinel.RedisSentinel;
import com.jfinal.kit.PropKit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;


public class test {
    
    public static void main(String[] args) {
        RedisPool redisPool = RedisCluster.start(PropKit.use("/conf/redis.properties"));
        JedisCluster jedisCluster = redisPool.getJedisCluster();
        jedisCluster.set("", "");
        
        RedisPool redisPool2 = RedisSentinel.start(PropKit.use("/conf/redis.properties"));
        Jedis jedis = redisPool2.getJedisSentinelPool();
        jedis.set("", "");
    }

}
