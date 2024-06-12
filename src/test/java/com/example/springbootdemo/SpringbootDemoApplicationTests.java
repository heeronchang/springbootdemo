package com.example.springbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

@SpringBootTest
@Slf4j
class SpringbootDemoApplicationTests {

    @Autowired
    RedissonClient redissonClient;

    // 计数器
    private int count;

    @Test
    void testDistLock() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                // 每个线程都创建自己的锁对象
                // 这是基于 Redis 实现的分布式锁
                Lock lock = this.redissonClient.getLock("counterLock");
                try {
                    // 上锁
                    lock.lock();
                    // 计数器自增 1
                    this.count = this.count + 1;
                } finally {
                    // 释放锁
                    lock.unlock();
                }
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        log.info("count = {}", this.count);
    }
}
