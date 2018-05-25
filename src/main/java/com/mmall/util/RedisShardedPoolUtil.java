package com.mmall.util;

import com.mmall.common.RedisPool;
import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;

@Slf4j
public class RedisShardedPoolUtil {

    public static void set (String key,String value){
        ShardedJedis jedis = null;
        try {
            jedis = RedisShardedPool.getJedis();
            jedis.set(key,value);
        } catch (Exception e) {
            log.error("set key:{} error",key,e);
        } finally {
            jedis.close();
        }
    }

    public static void setex(String key,String value,int expireTime){
        ShardedJedis jedis = null;
        try {
            jedis = RedisShardedPool.getJedis();
            jedis.setex(key,expireTime,value);
        } catch (Exception e) {
            log.error("setex key:{} error",key,e);
        } finally {
            jedis.close();
        }
    }

    public static String get (String key){
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error",key,e);
        } finally {
            jedis.close();
        }
        return result;
    }

    public static void del (String key){
        ShardedJedis jedis = null;
        try {
            jedis = RedisShardedPool.getJedis();
            jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} error",key,e);
        } finally {
            jedis.close();
        }
    }

    public static void expire (String key,int time){
        ShardedJedis jedis = null;
        try {
            jedis = RedisShardedPool.getJedis();
            jedis.expire(key,time);
        } catch (Exception e) {
            log.error("expire key:{} error",key,e);
        } finally {
            jedis.close();
        }
    }
}
