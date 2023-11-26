package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.repository.BaseRepositoryImpl;
import com.programming.courseservice.domain.persistent.entity.CourseProgress;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CourseProgressRepository extends BaseRepository<CourseProgress> {
    @Query("select cp from CourseProgress cp where cp.userId = :userId and cp.course.id = :courseId")
    Optional<CourseProgress> findByUserIdAndCourseId(@Param("userId") String userId, @Param("courseId") String courseId);

    @Transactional
    @Modifying
    @Query("""
                UPDATE CourseProgress cp SET cp.currentProgress = :currentProgress 
                WHERE cp.userId = :userId AND cp.course.id = :courseId
           """)
    void updateCurrentProgress(@Param("userId") String userId,
                               @Param("courseId") String courseId,
                               @Param("currentProgress") Integer currentProgress);
}
