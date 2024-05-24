package com.topilmalar.projection;

import com.topilmalar.entity.Roles;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserPro {
    Long getId();
    String getFirst_name();
    String getLast_name();
    String getUsername();
    Integer getAge();
    String getPhone_number();
//    @Value("#{@roleRepo.findAllByUserId()}")
//    List<Roles> getRoles();
}
