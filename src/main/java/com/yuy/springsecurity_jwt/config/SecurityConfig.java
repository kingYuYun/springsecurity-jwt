package com.yuy.springsecurity_jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
/**
 * @EnableWebSecurity是开启SpringSecurity的默认行为
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用全局方法安全
public class SecurityConfig{
    @Autowired
    private TokenAuthorizationManager tokenAuthorizationManager;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().antMatchers("/security/login", "/security/login/**").permitAll()
                .anyRequest().access(tokenAuthorizationManager);

        //关闭session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //关闭缓存
        http.headers().cacheControl().disable();

        http.csrf().disable();
        //返回
        return http.build();
    }


    /**
     * 指定加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }

}
