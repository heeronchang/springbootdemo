package com.example.springbootdemo.configuration;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class CacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getSimpleName()).append(":");
        sb.append(method.getName()).append(":");

        if (params.length != 0) {
            sb.append(SimpleKeyGenerator.generateKey(params));
        } else {
            sb.append(SimpleKey.EMPTY);
        }
// springbootdemouserinfo::UserInfoServiceImpl:findOne:2
        return sb.toString();
    }
}
