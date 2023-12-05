package com.example.springbootdemo.controller;

import com.example.springbootdemo.pojo.entity.User;
import com.example.springbootdemo.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService jpaUserService;

    @PostMapping("")
    public User addUser(@RequestBody User user) {
        return jpaUserService.insert(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        jpaUserService.delete(id);
    }

    @PutMapping("")
    public User update(@RequestBody User user) {
        return jpaUserService.update(user);
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable("id") Long id) {
        return jpaUserService.user(id);
    }

    @GetMapping("")
    public List<User> findAll() {
        return jpaUserService.users();
    }
}
