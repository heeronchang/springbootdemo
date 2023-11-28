package com.example.springbootdemo.repository;

import com.example.springbootdemo.pojo.entity.JpaUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<JpaUser, Long> {
}
