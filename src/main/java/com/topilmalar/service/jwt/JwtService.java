package com.topilmalar.service.jwt;

import com.topilmalar.entity.Users;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
    String generateAccessToken(Users byUsername);

    String extractSubject(String token);

    String generateRefreshToken(Users users);
}
