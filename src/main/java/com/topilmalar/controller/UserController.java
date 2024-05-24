package com.topilmalar.controller;

import com.topilmalar.service.UserService;
import com.topilmalar.service.jwt.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;

    @GetMapping
    public HttpEntity<?> getCurrentUser(@RequestHeader String Authorization){
        String subject = jwtService.extractSubject(Authorization);
        System.out.println("subject is: "+subject);
        if(!Objects.equals(subject, "token expired")){
            return userService.getCurrenUser(Long.parseLong(subject));
        }
        return ResponseEntity.ok(subject);
    }
}
