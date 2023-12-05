package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.pojo.entity.User;
import com.example.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Test
    void insert() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void user() {
    }

    @Test
    void users() {
    }

    @Test
    void usersWithCondition() {
        User user = new User();
        user.setId(1L);
        user.setName("hc666");

        Page<User> res = userService.user(user, 0, 3);
        log.info("res.content: {}, res.pageable: {}", res.getContent(), res.getPageable());
    }
}