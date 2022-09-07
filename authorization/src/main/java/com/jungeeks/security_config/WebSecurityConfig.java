package com.jungeeks.security_config;

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

//    private final AppUserService appUserService;

//    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }

//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider provider =
//                new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(bCryptPasswordEncoder);
//        provider.setUserDetailsService(appUserService);
//        return provider;
//    }

    @Bean
    public PrincipalExtractor principalExtractor(
//            UserDetailsRepo userDetailsRepo
    ) {
        return map -> {
            String id = (String) map.get("sub");
//            return userDetailsRepo.findById(id).orElseGet(() -> {
//                User newUser = new User();
            String idi = id;
            String name = (String) map.get("name");
            String email = (String) map.get("email");

//                newUser.setId(id);
//                newUser.setName((String) map.get("name"));
//                newUser.setEmail((String) map.get("email"));
//                userDetailsRepo.save(newUser);
//                return newUser;
//            });
            return null;
        };
    }
}
