package com.example.springbootdemo.service;

import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springbootdemo.domain.MyUserDetails;
import com.example.springbootdemo.domain.R;
import com.example.springbootdemo.domain.entity.User;
import com.example.springbootdemo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class AuthService {
    @Value("jtw.secret")
    private String secret;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    private final IUserService userService;

    @Autowired
    public AuthService(IUserService userService) {
        this.userService = userService;
    }

    public R<?> login(String username, String password) {
        // TODO 校验验证码

        // 使用ProviderManager auth方法进行验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        // 校验失败了
        if (Objects.isNull(authenticate)) {
            return R.fail("用户名或密码错误！");
        }

        MyUserDetails myUserDetails = (MyUserDetails) authenticate.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", myUserDetails.getId());
        claims.put("username", myUserDetails.getUsername());
        // TODO 过期时间
        String token = jwtUtils.createToken(claims);

        return R.ok(token);
    }

    public R<?> refreshToken() {
        return R.ok();
    }
}
