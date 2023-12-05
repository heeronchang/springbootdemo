package com.example.springbootdemo.service;

import com.example.springbootdemo.pojo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserService {
    User insert(User user);

    void delete(Long id);

    User update(User user);

    User user(Long id);

    List<User> users();

    Page<User> user(User user, int pageNum, int pageSize);
}
