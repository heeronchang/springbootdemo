package com.example.springbootdemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ping")
public class PingController {

    @GetMapping("/")
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('auth:test')")
    public List<String> users() {
        return List.of("1", "2", "3");
    }

}
