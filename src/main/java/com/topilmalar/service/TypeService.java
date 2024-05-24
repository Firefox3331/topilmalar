package com.topilmalar.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
public interface TypeService {
    HttpEntity<?> getTypes();

    HttpEntity<?> getSubTypes(Long typeId);

    HttpEntity<?> getRegions();

    HttpEntity<?> getDistricts(Long regionId);
}
