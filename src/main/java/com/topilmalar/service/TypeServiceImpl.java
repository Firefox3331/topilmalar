package com.topilmalar.service;

import com.topilmalar.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    LostItemRepo lostItemRepo;
    @Autowired
    TypeRepo typeRepo;
    @Autowired
    SubTypeRepo subTypeRepo;
    @Autowired
    RegionRepo regionRepo;
    @Autowired
    DistrictRepo districtRepo;
    @Autowired
    UserRepo userRepo;

    @Override
    public HttpEntity<?> getTypes() {
        return ResponseEntity.ok(typeRepo.findAll());
    }

    @Override
    public HttpEntity<?> getSubTypes(Long typeId) {
        return ResponseEntity.ok(subTypeRepo.findAllByTypeId(typeId));
    }

    @Override
    public HttpEntity<?> getRegions() {
        return ResponseEntity.ok(regionRepo.findAll());
    }

    @Override
    public HttpEntity<?> getDistricts(Long regionId) {
        return ResponseEntity.ok(districtRepo.findAllByRegionId(regionId));
    }
}
