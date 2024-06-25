package com.example.springbootdemo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/ping")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class PingController {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String ping() {
        return "pong";
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN:ADD')")
    public void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("X-TEST", "add success");
        response.getWriter().write("add");
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN:DELETE')")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write("delete");
    }
}
