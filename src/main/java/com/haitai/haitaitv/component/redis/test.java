package com.haitai.haitaitv.component.redis;


import com.haitai.haitaitv.component.redis.cluster.RedisCluster;
import com.haitai.haitaitv.component.redis.cluster.RedisPool;
import com.haitai.haitaitv.component.redis.sentinel.RedisSentinel;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;


public class test {
    
    public static void main(String[] args) {
        RedisPool redisPool = RedisCluster.start(null);
        JedisCluster jedisCluster = redisPool.getJedisCluster();
        jedisCluster.set("", "");
        
        RedisPool redisPool2 = RedisSentinel.start(null);
        Jedis jedis = redisPool2.getJedisSentinelPool();
        jedis.set("", "");
    }

}
