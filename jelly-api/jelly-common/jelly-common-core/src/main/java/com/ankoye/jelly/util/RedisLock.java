package com.ankoye.jelly.util;

import redis.clients.jedis.Jedis;

import java.util.Collections;
import java.util.UUID;

/**
 * jedis version 2.9.0
 * @author ankoye@qq.com
 */
public class RedisLock {
    /** 获取锁成功标识 */
    private static final String LOCK_SUCCESS = "OK";
    /** 获取锁失败标识 */
    private static final Long RELEASE_SUCCESS = 1L;
    /** 不存在则设置 */
    private static final String SET_IF_NOT_EXIST = "NX";
    /** 设置过期时间 */
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    /** 锁等待，防止线程饥饿 */
    private static final int ACQUIRE_TIMEOUT = 1000;
    /** redis 客户端 */
    private static final Jedis JEDIS;

    static  {
        JEDIS = new Jedis("jelly.com", 6379);
        JEDIS.auth("redis.160123");
    }

    public static String tryLock(String key, Integer expireTime) {
        // 随机生成一个value
        String requireToken = UUID.randomUUID().toString();
        // 获取锁的超时时间，超过这个时间则放弃获取锁
        long end = System.currentTimeMillis() + ACQUIRE_TIMEOUT;
        while (System.currentTimeMillis() < end) {
            Object result = JEDIS.set(key, requireToken, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
            if (LOCK_SUCCESS.equals(result)) {
                return requireToken;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return null;
    }

    public static boolean unlock(String key, String identify) {
        if(identify == null){
            return false;
        }
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = JEDIS.eval(script, Collections.singletonList(key), Collections.singletonList(identify));
        return RELEASE_SUCCESS.equals(result);
    }
}
