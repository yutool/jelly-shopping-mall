package com.ankoye.jelly.seckill;

import com.ankoye.jelly.seckill.common.constant.RedisKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class SeckillApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test() {
        System.out.println(redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY).get("1257199942"));

        System.out.println(redisTemplate.boundHashOps(RedisKey.SECKILL_GOODS_KEY+"2020051310").get("1257199942"));
    }
}
