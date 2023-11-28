package com.example.springbootdemo.service;

import com.example.springbootdemo.pojo.entity.JpaUser;

import java.util.List;

public interface JpaUserService {
    JpaUser insert(JpaUser user);
    void delete(Long id);
    JpaUser jpaUser(Long id);
    List<JpaUser> jpaUser();
    JpaUser update(JpaUser user);
}
