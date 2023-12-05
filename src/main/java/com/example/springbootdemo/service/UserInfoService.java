package com.example.springbootdemo.service;

import com.example.springbootdemo.pojo.UserInfo;

public interface UserInfoService {
    UserInfo add(UserInfo userInfo);

    UserInfo findOne(Long id);

    String test(int i, int j);
}
