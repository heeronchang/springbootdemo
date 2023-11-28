package com.example.springbootdemo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public List<String> users() {
        return List.of("1","2","3");
    }
}
