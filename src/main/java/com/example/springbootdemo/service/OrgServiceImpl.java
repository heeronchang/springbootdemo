package com.example.springbootdemo.service;

import com.example.springbootdemo.pojo.entity.Org;
import com.example.springbootdemo.repository.OrgRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgServiceImpl implements OrgService {

    @Resource
    OrgRepository orgRepository;

    @Override
    public Org insert(Org org) {
        return orgRepository.save(org);
    }

    @Override
    public void delete(Long id) {
        orgRepository.deleteById(id);
    }

    @Override
    public Org update(Org org) {
        return orgRepository.save(org);
    }

    @Override
    public Org findOne(Long id) {
        return orgRepository.findById(id).orElse(null);
    }

    @Override
    public List<Org> findAll() {
        return orgRepository.findAll();
    }
}
