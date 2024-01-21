package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.UserQuiz;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuizRepository extends BaseRepository<UserQuiz> {
}
