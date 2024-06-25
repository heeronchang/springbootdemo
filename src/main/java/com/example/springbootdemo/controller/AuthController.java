package com.example.springbootdemo.controller;

import com.example.springbootdemo.domain.R;
import com.example.springbootdemo.domain.dto.LoginReq;
import com.example.springbootdemo.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    R<?> login(@RequestBody LoginReq loginReq, HttpServletRequest request, HttpServletResponse response) {
        R<?> r = authService.login(loginReq.getUsername(), loginReq.getPassword());
        response.setHeader("Authorization", "Bearer " + r.getData());
        return r;
    }
}
