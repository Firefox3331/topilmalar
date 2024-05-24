package com.topilmalar.config;

import com.topilmalar.entity.Roles;
import com.topilmalar.entity.Users;
import com.topilmalar.repository.RoleRepo;
import com.topilmalar.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CommanLineConfig implements CommandLineRunner {

    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;

    @Override
    public void run(String... args) throws Exception {
        List<Roles> roles = roleRepo.saveAll(
                List.of(
                        new Roles("ROLE_SUPER_ADMIN"),
                        new Roles("ROLE_ADMIN"),
                        new Roles("ROLE_USER")
                )
        );
        userRepo.save(Users.builder()
                .firstName("superadmin")
                .lastName("superadmin")
                .username("superadmin")
                .age(23)
                .phone_number("+998953500075")
                .passport("AB8987801")
                .is_enabled(true)
                .roles(roles)
                .build()
        );
    }
}
