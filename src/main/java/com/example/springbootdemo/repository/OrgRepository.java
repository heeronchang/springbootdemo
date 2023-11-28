package com.example.springbootdemo.repository;

import com.example.springbootdemo.pojo.entity.Org;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgRepository extends JpaRepository<Org,Long> {
}
