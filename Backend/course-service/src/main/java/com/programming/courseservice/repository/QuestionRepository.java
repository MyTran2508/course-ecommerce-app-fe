package com.programming.courseservice.repository;

import com.programming.courseservice.domain.persistent.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Query(value = "SELECT * FROM question WHERE ex_quiz_id = :exQuizId", nativeQuery = true,
            countQuery = "SELECT count(*) FROM question WHERE ex_quiz_id = :exQuizId")
    Page<Question> findByExQuizId(String exQuizId, Pageable pageable);
}
