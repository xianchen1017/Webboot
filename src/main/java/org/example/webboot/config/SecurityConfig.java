package org.example.webboot.config;

import org.example.webboot.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // 注入 JwtAuthenticationFilter
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/images/**").permitAll()  // 允许访问 /images/** 路径
                .antMatchers("/api/auth/**").permitAll() // 允许认证路由的访问
                .antMatchers("/api/contact/list").permitAll()  // 允许匿名用户访问联系人列表
                .antMatchers("/api/user/list").permitAll()  // 允许匿名用户访问用户列表
                .antMatchers("/api/user/info").permitAll()  // 允许访问 /api/user/info
                .anyRequest().authenticated(); // 其他请求需要认证

        // 将 JWT 过滤器加入到 Spring Security 的过滤链中
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
