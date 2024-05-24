package com.topilmalar.controller;

import com.topilmalar.entity.Users;
import com.topilmalar.payload.Login;
import com.topilmalar.payload.UserDto;
import com.topilmalar.repository.UserRepo;
import com.topilmalar.service.UserService;
import com.topilmalar.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/register")
    public HttpEntity<?> saveUser(@RequestBody UserDto userDto){
        return userService.saveUser(userDto);
    }

    @PostMapping("/login")
    public HttpEntity<?> loginUser(@RequestBody Login login){
        return userService.loginUser(login);
    }

    @GetMapping("/refresh")
    public String refreshToken(@RequestHeader String refresh_token){
        String id = jwtService.extractSubject(refresh_token);
        Users users = userRepo.findById(Long.parseLong(id)).orElseThrow();
        return jwtService.generateAccessToken(users);
    }

    @GetMapping("/parse")
    public String parseToken(@RequestHeader String token){
        String id = jwtService.extractSubject(token);
        Optional<Users> userOpt = userRepo.findById(Long.parseLong(id));
        if(userOpt.isPresent()){
            return "success";
        }
        return "user not found";
    }


}
