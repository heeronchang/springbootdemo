package com.example.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heeron
 * @since 2024-06-12 18:00:21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/users")
    public List<String> users() {
        return List.of("user1", "user2", "user3");
    }
}
