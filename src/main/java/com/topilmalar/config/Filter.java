package com.topilmalar.config;

import com.topilmalar.repository.UserRepo;
import com.topilmalar.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Configuration
public class Filter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String refreshToken = request.getHeader("refresh_token");
            if(!Objects.equals(refreshToken, "")){
                String token = request.getHeader("Authorization");
                System.out.println(request.getRequestURI());
                System.out.println(token);
                if(token != null){
                    String id = jwtService.extractSubject(token);
                    if(!id.equals("token expired")){
                        UserDetails users = userRepo.findById(Long.parseLong(id)).orElseThrow();
                        SecurityContextHolder.getContext().setAuthentication(
                                new UsernamePasswordAuthenticationToken(
                                        users.getUsername(), null, users.getAuthorities()
                                )
                        );
                    }
                }
            };
        }catch(Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
}
