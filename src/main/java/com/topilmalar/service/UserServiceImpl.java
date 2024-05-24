package com.topilmalar.service;

import com.topilmalar.entity.Users;
import com.topilmalar.payload.Login;
import com.topilmalar.payload.UserDto;
import com.topilmalar.projection.UserPro;
import com.topilmalar.repository.RoleRepo;
import com.topilmalar.repository.UserRepo;
import com.topilmalar.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public HttpEntity<?> saveUser(UserDto userDto) {
        Users user = Users.builder()
                        .firstName(userDto.firstName())
                        .lastName(userDto.lastName())
                        .username(userDto.username())
                        .password(passwordEncoder.encode(userDto.password()))
                        .age(userDto.age())
                        .passport(userDto.passport())
                        .phone_number(userDto.phoneNumber()).build();

        userRepo.save(user);
        return ResponseEntity.ok("success");
    }

    @Override
    public HttpEntity<?> loginUser(Login login) {
        Users user = userRepo.findByUsername(login.username());
        System.out.println("user is "+user);
        Optional<Users> byId = userRepo.findById(user.getId());
        if(byId.get().isEnabled()){
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login.username(), login.password()
                    )
            );
            userRepo.save(byId.get());
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", jwtService.generateAccessToken(user));
            tokens.put("refresh_token", jwtService.generateRefreshToken(user));

            return ResponseEntity.ok(tokens);
        }
        return ResponseEntity.ok("user was blocked");
    }

    @Override
    public HttpEntity<?> getCurrenUser(Long userId) {
        Optional<UserPro> userOpt = userRepo.findByUserId(userId);
        if(userOpt.isPresent()){
            return ResponseEntity.ok(userOpt.get());
        }
        return ResponseEntity.ok("user not found!");
    }
}
