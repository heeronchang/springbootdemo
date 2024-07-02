package com.example.springbootdemo.controller;

import com.alibaba.fastjson2.JSON;
import com.example.springbootdemo.domain.R;
import com.example.springbootdemo.domain.entity.User;
import com.example.springbootdemo.exception.TestException;
import com.example.springbootdemo.security.authorize.jwt.JwtTokenAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/ping")
public class PingController {

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public R<?> ping() throws TestException {
//        return R.ok("pong");
        throw new TestException("test");
    }

    @PostMapping
    @PreAuthorize("hasRole('STAFF')")
    public R<?> add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("X-TEST", "add success");
        JwtTokenAuthentication authentication = (JwtTokenAuthentication) SecurityContextHolder.getContext().getAuthentication();
        User user = JSON.parseObject(authentication.getCurrentUser().toString(), User.class);
        return R.ok("add");
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN:DELETE')")
    public R<?> delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return R.ok("delete");
    }
}
