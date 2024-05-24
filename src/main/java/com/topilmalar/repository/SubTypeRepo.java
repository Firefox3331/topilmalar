package com.topilmalar.repository;

import com.topilmalar.entity.SubType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubTypeRepo extends JpaRepository<SubType, Long> {
    List<SubType> findAllByTypeId(Long typeId);
}
