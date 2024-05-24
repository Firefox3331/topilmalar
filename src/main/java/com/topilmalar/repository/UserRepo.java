package com.topilmalar.repository;

import com.topilmalar.entity.Users;
import com.topilmalar.projection.UserPro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Users, Long> {

    @Query(value = "select * from users where username = :username", nativeQuery = true)
    Users findByUsername(String username);
    @Query(value = "select * from users where id = :userId", nativeQuery = true)
    Optional<UserPro> findByUserId(Long userId);
}
