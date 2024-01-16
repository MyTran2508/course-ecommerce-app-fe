package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.ExQuiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExQuizRepository extends BaseRepository<ExQuiz> {

    @Query("""
                SELECT exQuiz
                FROM ExQuiz exQuiz
                WHERE exQuiz.lecture.id = :lectureId
            """)
    Page<ExQuiz> searchExQuiz(@Param("lectureId") String lectureId, Pageable pageable);
}
