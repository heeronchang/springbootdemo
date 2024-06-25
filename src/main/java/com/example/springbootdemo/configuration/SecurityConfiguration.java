package com.example.springbootdemo.configuration;

import com.example.springbootdemo.filter.JwtTokenFilter;
import com.example.springbootdemo.service.MyUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfiguration {
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable) // 禁用CSRF
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/captcha")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessHandler((req, resp, authentication) -> {
                            SecurityContextHolder.clearContext();
                        })
                )
                .userDetailsService(myUserDetailsService)
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


    /**
     * 身份验证管理器
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        return authenticationManager;
    }

    /**
     * 密码加密器
     * 主要是用来指定数据库中存储密码的加密方式,保证密码非明文存储
     * 当security需要进行密码校验时,会把请求传进来的密码进行加密,然后和数据库中的密码进行比对
     */
    @Bean
    PasswordEncoder bcrptyEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        String password = new BCryptPasswordEncoder().encode("123456");
        log.warn("password:{}", password);
    }
}
