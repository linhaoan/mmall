package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Slf4j
public class RedisPoolUtil {

    public static void set (String key,String value){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.set(key,value);
        } catch (Exception e) {
            log.error("set key:{} error",key,e);
        } finally {
            jedis.close();
        }
    }

    public static void setex(String key,String value,int expireTime){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.setex(key,expireTime,value);
        } catch (Exception e) {
            log.error("setex key:{} error",key,e);
        } finally {
            jedis.close();
        }
    }

    public static String get (String key){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error",key,e);
        } finally {
            jedis.close();
        }
        return result;
    }

    public static void del (String key){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{} error",key,e);
        } finally {
            jedis.close();
        }
    }

    public static void expire (String key,int time){
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            jedis.expire(key,time);
        } catch (Exception e) {
            log.error("expire key:{} error",key,e);
        } finally {
            jedis.close();
        }
    }

    public static void main(String[] args) {
        RedisPoolUtil.del("geelykey123123");
    }
}
