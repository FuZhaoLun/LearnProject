package com.example.redis;

import com.alibaba.fastjson.JSONObject;
import com.example.redis.service.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class RedisApplicationTests {
    @Autowired
    CacheService cacheService;

    @Test
    void testAdd() {
        String value = "{\"name\":\"fuzhaolun\",\"age\":\"25\",\"sex\":\"man\",\"birthday\":\"1997-04-04\"}";
        Random random = new Random();
        cacheService.add(random.nextInt(10000), JSONObject.parseObject(value));
    }

    @Test
    void testDelete() {
        cacheService.delete("test");
    }
}