package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.pojo.entity.User;
import com.example.springbootdemo.repository.UserRepository;
import com.example.springbootdemo.service.UserService;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository jpaUserRepository;

    @Autowired
    public UserServiceImpl(UserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }


    @Override
    public User insert(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public User update(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public User user(Long id) {
        return jpaUserRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> users() {
        return jpaUserRepository.findAll();
    }

    @Override
    // TODO User 入参使用一个UserCondition类是否为最优方式？
    public Page<User> user(User user, int pageNumber, int pageSize) {
        // 分页 排序
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name"));

        // 条件查询
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<>();
//                Path<User> path = root.get("id");
//                Predicate predicate = criteriaBuilder.equal(path, "1");
//                predicateList.add(predicate);

                Predicate predicate1 = criteriaBuilder.equal(root.get("account"),"heeron");
                predicateList.add(predicate1);
                // 当查询条件用到gt, ge, lt, le, like（分别表示>, >=, <, <=,模糊查询）时
                // 使用 .as(String.class) 表明查询对象属性的类型
                Predicate namePre = criteriaBuilder.like(root.get("name").as(String.class), "hc%");
                predicateList.add(namePre);

                return query.where(predicateList.toArray(new Predicate[0])).getRestriction();
            }
        };


        // 定义返回类，从新封装返回前端
        return  jpaUserRepository.findAll(spec, pageable);
    }

}
