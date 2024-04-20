package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuizRepository extends BaseRepository<UserQuiz> {

    UserQuiz findByUserIdAndExQuizId(String userId, String exQuizId);

    @Query("""
                SELECT MAX(uq.attemptNumber) + 1
                FROM UserQuiz uq
                WHERE uq.userId = :userId
                AND uq.exQuizId = :exQuizId
            """)
    Integer getNextAttemptNumber(String userId, String exQuizId);
}
