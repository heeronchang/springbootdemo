package com.example.springbootdemo.service;

import com.example.springbootdemo.domain.entity.User;
import com.example.springbootdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testSelect() {
        log.debug("select all users");
        List<User> users = userMapper.selectList(null);
        users.forEach(user -> log.debug(user.toString()));
    }
}
