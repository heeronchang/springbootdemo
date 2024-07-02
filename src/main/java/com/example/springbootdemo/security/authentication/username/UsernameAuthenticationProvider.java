package com.example.springbootdemo.security.authentication.username;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springbootdemo.domain.entity.User;
import com.example.springbootdemo.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class UsernameAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsernameAuthenticationProvider() {
        super();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 用户提交的用户名 + 密码：
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // 查数据库，匹配用户信息
        QueryWrapper<User> queryWrapper = Wrappers.query();
        queryWrapper.eq("username", username);
        queryWrapper.last("limit 1");
        User user = userService.getOne(queryWrapper);
        if (Objects.isNull(user) || !passwordEncoder.matches(password, user.getPassword())) {
            log.error("用户名或密码错误");

            // 密码错误，直接抛异常。
            // 根据SpringSecurity框架的代码逻辑，认证失败时，应该抛这个异常：org.springframework.security.core.AuthenticationException
            // BadCredentialsException就是这个异常的子类
            // 抛出异常后后，AuthenticationFailureHandler的实现类会处理这个异常。
            throw new BadCredentialsException("用户名或密码错误");
        }

        // 权限
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_ADMIN:ADD");

        // 构建认证对象
        UsernameAuthentication usernameAuthentication = new UsernameAuthentication(authorities);
        usernameAuthentication.setCurrentUser(user);
        usernameAuthentication.setAuthenticated(true); // 认证通过，设置为 true

        return usernameAuthentication;
    }

    // 判断当前AuthenticationProvider是否能处理给定类型的Authentication对象
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernameAuthentication.class);
    }
}
