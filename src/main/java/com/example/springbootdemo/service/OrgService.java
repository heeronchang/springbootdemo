package com.example.springbootdemo.service;

import com.example.springbootdemo.pojo.entity.Org;
import java.util.List;

public interface OrgService {
    Org insert(Org org);
    void delete(Long id);
    Org update(Org org);
    Org findOne(Long id);
    List<Org> findAll();
}
