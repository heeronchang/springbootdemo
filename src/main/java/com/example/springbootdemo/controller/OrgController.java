package com.example.springbootdemo.controller;

import com.example.springbootdemo.pojo.entity.Org;
import com.example.springbootdemo.service.OrgService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/org")
public class OrgController {

    @Resource
    OrgService orgService;

    @GetMapping("")
    public List<Org> findAll() {
        return orgService.findAll();
    }
}
