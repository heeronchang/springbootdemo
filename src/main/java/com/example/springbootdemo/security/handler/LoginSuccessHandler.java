package com.example.springbootdemo.security.handler;

import com.alibaba.fastjson2.JSON;
import com.example.springbootdemo.domain.R;
import com.example.springbootdemo.utils.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 认证成功/登录成功 事件处理器
 */
@Component
public class LoginSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtUtils jwtUtils;

    public LoginSuccessHandler() {
        this.setRedirectStrategy((request, response, url) -> {
            // do nothing, no redirect in REST.
        });
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        // 生成token和refreshToken
        Map<String, Object> responseData = new LinkedHashMap<>();

        Map<String, Object> claims = new HashMap<>();
        claims.put("user", JSON.toJSONString(principal));
        claims.put("authorities", JSON.toJSONString(authorities));

        // TODO refresh token
//        responseData.put("refreshToken", jwtUtils.createRefreshToken(claims));
        responseData.put("token", jwtUtils.createToken(claims));

//        // 一些特殊的登录参数。比如三方登录，需要额外返回一个字段是否需要跳转的绑定已有账号页面
//        Object details = authentication.getDetails();
//        if (details instanceof Map) {
//            Map detailsMap = (Map)details;
//            responseData.putAll(detailsMap);
//        }

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(R.ok(responseData, "登录成功")));
        writer.flush();
        writer.close();
    }
}
