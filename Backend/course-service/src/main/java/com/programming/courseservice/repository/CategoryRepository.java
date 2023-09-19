package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.core.persistent.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {
    Optional<Category> findByName(String name);
}
