package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.Assignment;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignmentRepository extends BaseRepository<Assignment> {
}
