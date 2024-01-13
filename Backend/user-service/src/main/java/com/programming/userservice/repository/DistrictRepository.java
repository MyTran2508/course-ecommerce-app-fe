package com.programming.userservice.repository;

import com.programming.userservice.domain.persistent.entity.District;
import com.programming.userservice.domain.persistent.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, String> {

    @Query("""
                SELECT d
                FROM District d
                WHERE d.province.code = :provinceCode
            """)
    List<District> findByDistrictByProvinceCode(String provinceCode);
}
