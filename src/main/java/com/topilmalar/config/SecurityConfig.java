package com.topilmalar.config;

import com.topilmalar.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Autowired
    UserRepo userRepo;

    @Autowired
    Filter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/refresh").permitAll()
                        .requestMatchers("/auth/parse").permitAll()
                        .requestMatchers("/api/lost/filter").permitAll()
                        .requestMatchers("/api/type").permitAll()
                        .requestMatchers("/api/type/region").permitAll()
                        .requestMatchers("/api/type/district/*").permitAll()
                        .requestMatchers("/api/type/subtype/*").permitAll()
                        .requestMatchers("/api/lost/all").permitAll()
                        .requestMatchers("/api/lost/statistika").permitAll()
                        .requestMatchers("/api/lost/search").permitAll()
                        .requestMatchers("/api/lost/test").permitAll()
                        .requestMatchers("/", "/index.html", "/static/**",
                                "/*.ico", "/*.json", "*.png").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return security.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> userRepo.findByUsername(username);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}