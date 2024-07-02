package com.example.springbootdemo.security.authorize.jwt;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.example.springbootdemo.domain.R;
import com.example.springbootdemo.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

//@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;

//    @Autowired
    public JwtTokenAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (StrUtil.isNotEmpty(token) && token.startsWith("Bearer ")) {
            token = StrUtil.subAfter(token, "Bearer ", false);
            boolean verify = jwtUtils.verifyToken(token);
            if (!verify) {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(JSON.toJSONString(R.fail("token失效")));
                return;
            } else {
                Object user = jwtUtils.parseToken(token).get("user");
                String authoritiesJson = jwtUtils.parseToken(token).get("authorities").toString();
                Collection<? extends GrantedAuthority> authorities = JSON.parseArray(authoritiesJson, GrantedAuthority.class);
                JwtTokenAuthentication jwtTokenAuthentication = new JwtTokenAuthentication(authorities);
                jwtTokenAuthentication.setCurrentUser(user);
                jwtTokenAuthentication.setJwtToken(token);
                jwtTokenAuthentication.setAuthenticated(true);

                // 设置用户信息
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(jwtTokenAuthentication);
                SecurityContextHolder.setContext(context);
            }
        }

        filterChain.doFilter(request, response);
    }
}
