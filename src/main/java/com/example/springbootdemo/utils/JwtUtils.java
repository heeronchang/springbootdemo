package com.example.springbootdemo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Map;

@Component
@Slf4j
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Map<String, Object> claims) {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    public Map<String, Object> parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    public boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("verify token error: {}", e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        // 使用io.jsonwebtoken.Jwts生成一个SignatureAlgorithm.HS512类型的secret
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String secret = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        log.info("secret: {}", secret);
    }
}
