package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizRepository extends BaseRepository<UserQuiz> {

    List<UserQuiz> findByUserIdAndExQuizIdOrderByAttemptNumberAsc(String userId, String exQuizId);

    UserQuiz findByUserIdAndExQuizIdAndAttemptNumber(String userId, String exQuizId, Integer attemptNumber);

    @Query("""
                SELECT MAX(uq.attemptNumber)
                FROM UserQuiz uq
                WHERE uq.userId = :userId
                AND uq.exQuizId = :exQuizId
            """)
    Integer getNextAttemptNumber(String userId, String exQuizId);
}
