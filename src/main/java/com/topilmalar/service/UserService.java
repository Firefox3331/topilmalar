package com.topilmalar.service;

import com.topilmalar.payload.Login;
import com.topilmalar.payload.UserDto;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    HttpEntity<?> saveUser(UserDto userDto);

    HttpEntity<?> loginUser(Login login);

    HttpEntity<?> getCurrenUser(Long userId);
}
