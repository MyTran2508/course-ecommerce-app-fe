package com.programming.courseservice.repository;

import com.programming.courseservice.domain.persistent.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, String> {
}
