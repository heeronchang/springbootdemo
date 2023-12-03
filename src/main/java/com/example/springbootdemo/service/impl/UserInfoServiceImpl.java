package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.mapper.UserInfoMapper;
import com.example.springbootdemo.pojo.UserInfo;
import com.example.springbootdemo.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo modify(UserInfo userInfo) {
        int res = userInfoMapper.modify(userInfo);
        return res > 0 ? userInfo : null;
    }

    @Override
    @Cacheable(value = "userinfo", keyGenerator = "cacheKeyGenerator")
    public UserInfo findOne(Long id) {
        return userInfoMapper.findOne(id);
    }

    // @CachePut
    // @CacheEvict

    @Override
    @Cacheable(value = "test")
    public String test(int i, int j) {
        return "test";
    }
}
