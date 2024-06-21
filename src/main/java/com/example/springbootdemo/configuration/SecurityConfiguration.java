package com.example.springbootdemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login/**", "/token/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
//                .formLogin(Customizer.withDefaults()) // 默认登录配置
                .formLogin(form -> form
                        .loginPage("/login") // 自定义登录页
                        .loginProcessingUrl("/login") // 登录表单提交的URL
                        .defaultSuccessUrl("/index") // 登录成功后跳转的URL
                        .failureForwardUrl("/login?error=true") // 登录失败后跳转的URL
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // 注销的URL
                        .logoutSuccessUrl("/login") // 注销后跳转的URL
                        .invalidateHttpSession(true)
//                        .deleteCookies("JSESSIONID") // 删除指定cookie
                )
//                .httpBasic(Customizer.withDefaults())
                .build();
    }
//
//    @Bean
//    UserDetailsService userDetailsService() {
//        UserDetails heeron = User.builder()
//                .username("hee")
//                .password(bcrptyEncoder().encode("123456"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(heeron);
//    }
//
//    @Bean
//    PasswordEncoder bcrptyEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
