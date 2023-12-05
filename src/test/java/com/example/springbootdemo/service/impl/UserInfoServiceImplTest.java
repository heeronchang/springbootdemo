package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.pojo.UserInfo;
import com.example.springbootdemo.service.RedisService;
import com.example.springbootdemo.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class UserInfoServiceImplTest {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    RedisService redisService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void add() {
        UserInfo uInfo = new UserInfo(null, "hc6", null, "chang6", "this is chang6 desc.");
        userInfoService.add(uInfo);
        log.info("added userInfo: {}", uInfo);
    }

    @Test
    void findOne() {
        UserInfo uInfo = userInfoService.findOne(2L);
        log.info("findOne userInfo: {}", uInfo);
    }

    @Test
    void test1() {
        userInfoService.test(1, 2);

        long expire = redisService.getExpire("springboottest::SimpleKey [1, 2]");
        log.info("expire: {}", expire);
    }
}