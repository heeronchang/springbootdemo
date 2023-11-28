package com.example.springbootdemo.controller;

import com.example.springbootdemo.pojo.entity.JpaUser;
import com.example.springbootdemo.service.JpaUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    JpaUserService jpaUserService;

    @PostMapping("")
    public JpaUser addUser(@RequestBody JpaUser user) {
        return jpaUserService.insert(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        jpaUserService.delete(id);
    }

    @PutMapping("")
    public JpaUser update(@RequestBody JpaUser user) {
        return jpaUserService.update(user);
    }

    @GetMapping("/{id}")
    public JpaUser findOne(@PathVariable("id") Long id) {
        return jpaUserService.jpaUser(id);
    }

    @GetMapping("")
    public List<JpaUser> findAll() {
        return jpaUserService.jpaUser();
    }
}
