package com.example.springbootdemo.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
@Slf4j
class RedisServiceTest {
    @Resource
    RedisService redisService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void cache() {
        redisService.cache("test", "12345");
    }

    @Test
    void cacheWithExpire() {
    }

    @Test
    void get() {
        String value = redisService.get("test");
        Assertions.assertEquals("12345", value);
        log.info("key:test value is {}", value);
    }

    @Test
    void enableExpire() {
    }

    @Test
    void getExpire() {
        long expire = redisService.getExpire("springboottest::SimpleKey [1, 2]");
        System.out.println(expire);

        long expire2 = redisService.getExpire("test::SimpleKey [1, 2]");
        System.out.println(expire2);

        long expire3 = redisService.getExpire("SimpleKey [1, 2]");
        System.out.println(expire3);
    }

    @Test
    void hasKey() {
    }

    @Test
    void delete() {
    }

    @Test
    void keys() {
        Set<String> set = redisService.keys("te*");
        log.info(set.toString());
        for (String key :
                set) {
            log.info(key);
        }
    }
}