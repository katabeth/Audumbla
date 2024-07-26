package com.sparta.audumbla.audumblaworldjpa.config;

import com.sparta.audumbla.audumblaworldjpa.service.AudumblaSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final AudumblaSecurity audumblaSecurity;

    @Autowired
    public SecurityConfig(AudumblaSecurity audumblaSecurity) {
        this.audumblaSecurity = audumblaSecurity;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "/welcome","/error") .permitAll()
                        .requestMatchers("/cities", "/countries", "/languages").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/create", "/delete","/update").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .userDetailsService(audumblaSecurity)
                .formLogin(Customizer.withDefaults())
                .build();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
