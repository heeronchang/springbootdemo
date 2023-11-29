package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.pojo.entity.JpaUser;
import com.example.springbootdemo.repository.JpaUserRepository;
import com.example.springbootdemo.service.JpaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JpaServiceImpl implements JpaUserService {

    private final JpaUserRepository jpaUserRepository;

    @Autowired
    public JpaServiceImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }


    @Override
    public JpaUser insert(JpaUser user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public JpaUser jpaUser(Long id) {
        return jpaUserRepository.findById(id).orElse(null);
    }

    @Override
    public List<JpaUser> jpaUser() {
        return jpaUserRepository.findAll();
    }

    @Override
    public JpaUser update(JpaUser user) {
        return jpaUserRepository.save(user);
    }
}
