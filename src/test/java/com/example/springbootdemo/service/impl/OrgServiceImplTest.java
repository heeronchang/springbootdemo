package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.pojo.entity.Org;
import com.example.springbootdemo.service.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class OrgServiceImplTest {
    @Autowired
    OrgService orgService;

    @Test
    void insert() {
        Org org = new Org();
        org.setName("chang");
        org.setDescription("this is chang org.");

        Org res = orgService.insert(org);
        log.info("added org: {}", res);
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void findOne() {
    }

    @Test
    void findAll() {
    }
}