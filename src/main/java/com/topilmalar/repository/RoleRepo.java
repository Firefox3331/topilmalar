package com.topilmalar.repository;

import com.topilmalar.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepo extends JpaRepository<Roles, Long> {
    @Query(value = "select r.id, r.name from roles r\n" +
            "join public.users_roles ur on r.id = ur.roles_id\n" +
            "where users_id = :userId;", nativeQuery = true)
    List<Roles> findAllByUserId(Long userId);
}
