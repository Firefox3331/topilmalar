package com.topilmalar.repository;

import com.topilmalar.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepo extends JpaRepository<District, Long> {
    List<District> findAllByRegionId(Long regionId);
}
