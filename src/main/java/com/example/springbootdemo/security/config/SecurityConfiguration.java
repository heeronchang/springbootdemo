package com.example.springbootdemo.security.config;

import com.example.springbootdemo.security.authentication.username.UsernameAuthenticationFilter;
import com.example.springbootdemo.security.authentication.username.UsernameAuthenticationProvider;
import com.example.springbootdemo.security.authorize.jwt.JwtTokenAuthenticationFilter;
import com.example.springbootdemo.security.filter.SecurityGlobalExceptionFilter;
import com.example.springbootdemo.security.handler.AuthenticationExceptionHandler;
import com.example.springbootdemo.security.handler.AuthorizationExceptionHandler;
import com.example.springbootdemo.security.handler.LoginFailureHandler;
import com.example.springbootdemo.security.handler.LoginSuccessHandler;
import com.example.springbootdemo.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;

@Configuration
@EnableWebSecurity // 开启Spring Security的Web安全支持，它会导入一个配置类，帮助设置HTTP安全（比如配置哪些URL需要被保护、哪些可以公开访问、认证和授权规则等）
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
// 启用Spring Security的方法安全支持，它允许你在方法或类级别上使用注解（如@PreAuthorize, @PostAuthorize, @Secured, @RolesAllowed等）来控制对方法的访问。如果不使用这个注解，Spring Security将不会解析这些方法级别的安全注解。
@Slf4j
public class SecurityConfiguration {
    private final ApplicationContext applicationContext;

    @Autowired
    public SecurityConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    SecurityFilterChain loginFilterChain(HttpSecurity httpSecurity) throws Exception {
        baseSettings(httpSecurity);

        httpSecurity
                .securityMatcher("/auth/**", "/error")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/error")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                );

        // 加一个登录方式。用户名、密码登录
        UsernameAuthenticationFilter usernameLoginFilter = new UsernameAuthenticationFilter(
                new AntPathRequestMatcher("/auth/username", HttpMethod.POST.name()),
                new ProviderManager(List.of(applicationContext.getBean(UsernameAuthenticationProvider.class))),
                applicationContext.getBean(LoginSuccessHandler.class),
                applicationContext.getBean(LoginFailureHandler.class));
        httpSecurity.addFilterBefore(usernameLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    // 业务接口授权控制
    @Bean
    SecurityFilterChain businessApiFilterChain(HttpSecurity httpSecurity) throws Exception {
        baseSettings(httpSecurity);
        httpSecurity
                .securityMatcher("/api/business/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .authenticated()
                );

        httpSecurity.addFilterBefore(
                new JwtTokenAuthenticationFilter(applicationContext.getBean(JwtUtils.class)),
                UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    // open api
    @Bean
    SecurityFilterChain openApiFilterChain(HttpSecurity httpSecurity) throws Exception {
        baseSettings(httpSecurity);
        httpSecurity
                .securityMatcher("/api/open/**")
                .authorizeHttpRequests(auth -> auth
                        .anyRequest()
                        .permitAll()
                );

        // no needs to add filter
        // httpSecurity.addFilterBefore();

        return httpSecurity.build();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_STAFF\nROLE_STAFF > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

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

    /**
     * 表单登录/登出、session管理、csrf防护等默认配置，如果不disable。会默认创建默认filter
     * requestCache用于重定向，前后分析项目无需重定向，requestCache也用不上
     *
     * @param httpSecurity
     * @throws Exception
     */
    private void baseSettings(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable).sessionManagement(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .requestCache(cache -> cache.requestCache(new NullRequestCache()))
                .anonymous(AbstractHttpConfigurer::disable);

        httpSecurity.exceptionHandling(exceptionHandling -> {
            exceptionHandling.authenticationEntryPoint(new AuthenticationExceptionHandler())
                    .accessDeniedHandler(new AuthorizationExceptionHandler());
        });

        // 其它未知异常，尽量提前加载
        httpSecurity.addFilterBefore(new SecurityGlobalExceptionFilter(), SecurityContextHolderFilter.class);
    }
}
