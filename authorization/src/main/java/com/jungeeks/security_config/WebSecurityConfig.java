package com.jungeeks.security_config;

import com.jungeeks.email.entity.User;
import com.jungeeks.email.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
/*
 *  These annotation transforms our application into an OAuth2 client.
 *  It instructs Spring to configure an OAuth2ClientAuthenticationProcessingFilter,
 *  along with other components that our application needs to be capable of obtaining access tokens from an authorization server.
 */
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserRepository userRepository) {
        return map -> {
            String sub = (String) map.get("sub");
            return userRepository.findBySub(sub).orElseGet(() -> {
                User newUser = new User();
                newUser.setSub(sub);
                newUser.setName((String) map.get("name"));
                newUser.setEmail((String) map.get("email"));
                userRepository.save(newUser);
                return newUser;
            });
        };
    }
}
