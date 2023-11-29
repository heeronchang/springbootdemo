package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.mapper.UserInfoMapper;
import com.example.springbootdemo.pojo.UserInfo;
import com.example.springbootdemo.pojo.entity.Org;
import com.example.springbootdemo.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public UserInfo modify(UserInfo userInfo) {
        int res = userInfoMapper.modify(userInfo);
        return  res > 0 ? userInfo : null;
    }

    @Override
    public UserInfo findOne(Long id) {
        return userInfoMapper.findOne(id);
    }
}
