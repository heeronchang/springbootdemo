package com.example.springbootdemo.mapper;

import com.example.springbootdemo.pojo.UserInfo;

public interface UserInfoMapper {
    int modify(UserInfo userInfo);
    UserInfo findOne(Long id);
}
