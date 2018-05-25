package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;
import sun.applet.Main;

import java.util.ArrayList;
import java.util.List;

public class RedisShardedPool {

    private static ShardedJedisPool pool;

    //最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));

    //在jedispool中最大的idle状态(空闲的)的jedis实例的个数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));

    //在jedispool中最小的idle状态(空闲的)的jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));

    //在borrow一个jedis实例的时候，是否要进行验证操作，如果赋值true。则得到的jedis实例肯定是可以用的。
    private static Boolean testOnBorrow  = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));

    //在return一个jedis实例的时候，是否要进行验证操作，如果赋值true。则放回jedispool的jedis实例肯定是可以用的。
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return","true"));


    private static String redis1Ip = PropertiesUtil.getProperty("redis1.redisIp","127.0.0.1");

    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.redisPort","6379"));


    private static String redis2Ip = PropertiesUtil.getProperty("redis2.redisIp","127.0.0.1");

    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.redisPort","6380"));

    static {
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        //连接耗尽的时候，是否阻塞，false会抛出异常，true阻塞直到超时。默认为true。
        config.setBlockWhenExhausted(true);

        JedisShardInfo jedisShardInfo1 = new JedisShardInfo(redis1Ip,redis1Port);
        JedisShardInfo jedisShardInfo2 = new JedisShardInfo(redis2Ip,redis2Port);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>(2);

        jedisShardInfoList.add(jedisShardInfo1);
        jedisShardInfoList.add(jedisShardInfo2);

        pool = new ShardedJedisPool(config,jedisShardInfoList,
                Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);

    }

    public static ShardedJedis getJedis() {
        return pool.getResource();
    }

    public static void closeJedis(ShardedJedis jedis){
        jedis.close();
    }

    public static void main(String[] args) {
        ShardedJedis jedis = pool.getResource();
        for(int i=0;i<10;i++) {
            jedis.set("key"+i,"value"+i);
        }
        closeJedis(jedis);

    }

}
