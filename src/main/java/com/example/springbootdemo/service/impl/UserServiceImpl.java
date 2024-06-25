package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.domain.entity.User;
import com.example.springbootdemo.mapper.UserMapper;
import com.example.springbootdemo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heeron
 * @since 2024-06-21 17:37:08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
