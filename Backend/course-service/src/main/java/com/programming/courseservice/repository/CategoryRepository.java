package com.programming.courseservice.repository;

import com.amazonaws.services.alexaforbusiness.model.Sort;
import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository<Category> {

    Optional<Category> findByName(String name);

    List<Category> findByNameOrderByIdDesc(String categoryName);
}
