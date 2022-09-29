package com.jungeeks.security_config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/registration/**").permitAll()
                .antMatchers("/email").permitAll()
                .antMatchers("/google").permitAll()
                .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
        return http.build();
    }
}
