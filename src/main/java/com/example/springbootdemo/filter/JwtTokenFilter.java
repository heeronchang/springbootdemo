package com.example.springbootdemo.filter;

import cn.hutool.core.util.StrUtil;
import com.example.springbootdemo.domain.MyUserDetails;
import com.example.springbootdemo.service.MyUserDetailsService;
import com.example.springbootdemo.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public JwtTokenFilter(MyUserDetailsService myUserDetailsService, JwtUtils jwtUtils) {
        this.myUserDetailsService = myUserDetailsService;
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
                response.getWriter().write("{\"code\":401,\"msg\":\"token验证失败\"}");
                return;
            } else {
                String userName = jwtUtils.parseToken(token).get("username").toString();
                MyUserDetails userDetail = (MyUserDetails) myUserDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetail, userDetail.getPassword(), userDetail.getAuthorities());
                // 设置用户信息
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
