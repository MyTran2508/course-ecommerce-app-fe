package com.programming.courseservice.repository;

import com.programming.courseservice.domain.persistent.entity.SecretKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecretKeyRepository extends JpaRepository<SecretKey, String> {
}
