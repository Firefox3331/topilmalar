package com.topilmalar.projection;


import org.springframework.beans.factory.annotation.Value;

public interface OwnerPro {
    Long getId();
    String getDescription();
    String getRegistry();
    String getStatus();
    @Value("#{target.owner.id}")
    Long getOwnerId();
    @Value("#{target.owner.firstName}")
    String getFirstName();
    @Value("#{target.owner.lastName}")
    String getLastName();
    @Value("#{target.owner.age}")
    Integer getAge();
    @Value("#{target.owner.phone_number}")
    String getPhone_number();
    @Value("#{target.users.firstName}")
    String getUserFirstName();
    @Value("#{target.users.lastName}")
    String getUserLastName();
    @Value("#{target.users.phone_number}")
    String getUserPhoneNumber();
}
