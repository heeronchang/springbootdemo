package com.example.springbootdemo.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.crypto.KeyGenerator;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class RedisConfig {
    @Value("#{${cache.ttl}}")
    private Map<String, Long> ttlParams;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);

        template.setValueSerializer(serializer());

        // afterPropertiesSet 是 Spring 框架中 InitializingBean 接口的一个方法。
        // 当一个类实现了 InitializingBean 接口时，Spring 在初始化 bean 时会调用 afterPropertiesSet 方法。
//        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration cfg = RedisCacheConfiguration.defaultCacheConfig();
        cfg = cfg
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer()))
                .disableCachingNullValues()
                .prefixCacheNameWith("springbootdemo")
                .entryTtl(Duration.ofMinutes(1L));

        Map<String, RedisCacheConfiguration> map = new HashMap<>();
        Set<Map.Entry<String, Long>> entries = ttlParams.entrySet();
        for (Map.Entry<String, Long> entry : entries) {
            map.put(entry.getKey(), cfg.entryTtl(Duration.ofMinutes(entry.getValue())));
            map.put("demo", cfg);
        }

        return RedisCacheManager
                .builder(factory)
                .cacheDefaults(cfg)
                .withInitialCacheConfigurations(map)
                .build();
    }

//    @Bean
//    public KeyGenerator keyGenerator() {
//        return (target, method, params) -> {
//            StringBuilder sb = new StringBuilder();
//
//            sb.append(target.getClass().getSimpleName()).append(":");
//            sb.append(method.getName()).append(":");
//            if (params != null && params.length > 0) {
//                sb.append(params[0]); // SimpleKeyGenerator.generateKey(params)
//            }
//
//            return sb.toString();
//        };
//    }

    private Jackson2JsonRedisSerializer<Object> serializer() {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(om.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        return new Jackson2JsonRedisSerializer<>(om, Object.class);
    }
}
