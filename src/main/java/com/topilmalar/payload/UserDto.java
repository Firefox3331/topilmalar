package com.topilmalar.payload;

public record UserDto(
        String firstName,
        String lastName,
        String username,
        String password,
        Integer age,
        String passport,
        String phoneNumber
) {
}
