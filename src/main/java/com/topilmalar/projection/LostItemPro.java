package com.topilmalar.projection;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface LostItemPro {
    Long getId();
    @Value("#{target.type.name}")
    String getType();
    @Value("#{target.subType.name}")
    String getSubType();
    @Value("#{target.region.name}")
    String getRegion();
    @Value("#{target.district.name}")
    String getDistrict();
    boolean isFound();
    LocalDateTime getLostDate();
    String getOrganization();
    String getStatus();
    @Value("#{target.users.username}")
    String getUsername();
    String getDescription();
    String getRegistry();
    Long getOwner_id();
}
