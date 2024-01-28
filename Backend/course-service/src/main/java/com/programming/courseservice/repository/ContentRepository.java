package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.Content;
import com.programming.courseservice.domain.persistent.entity.Course;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContentRepository extends BaseRepository<Content> {
    @Query("""
                select c from Content c
                where c.course.id = :courseId
            """)
    Optional<Content> findContentByCourseId(String courseId);
}
