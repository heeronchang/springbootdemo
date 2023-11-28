package com.example.springbootdemo.service;

import com.example.springbootdemo.pojo.UserInfo;
import com.example.springbootdemo.pojo.entity.Org;

import java.util.List;

public interface UserInfoService {
    UserInfo modify(UserInfo userInfo);
    UserInfo findOne(Long id);
}
