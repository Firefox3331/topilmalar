package com.topilmalar.controller;

import com.topilmalar.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/type")
public class TypeController {
    @Autowired
    TypeService typeService;

    @GetMapping
    public HttpEntity<?> getTypes(){
        return typeService.getTypes();
    }

    @GetMapping("/subtype/{typeId}")
    public HttpEntity<?> getSubTypes(@PathVariable Long typeId){
        return typeService.getSubTypes(typeId);
    }

    @GetMapping("/region")
    public HttpEntity<?> getRegions(){
        return typeService.getRegions();
    }

    @GetMapping("/district/{regionId}")
    public HttpEntity<?> getDistricts(@PathVariable Long regionId){
        return typeService.getDistricts(regionId);
    }
}
