package com.example.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springbootdemo.domain.MyUserDetails;
import com.example.springbootdemo.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper.eq("username", username);
        queryWrapper.last("limit 1");
        User user = userService.getOne(queryWrapper);
        if (Objects.isNull(user)) {
            log.error("用户名或密码错误");
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_ADMIN:ADD");
        MyUserDetails userDetails = new MyUserDetails(user, authorities);
        return userDetails;
    }
}
