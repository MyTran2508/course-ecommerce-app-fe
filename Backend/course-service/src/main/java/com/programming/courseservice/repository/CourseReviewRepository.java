package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.CourseReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseReviewRepository extends BaseRepository<CourseReview> {

    @Query("""
                SELECT cr FROM CourseReview cr
                WHERE cr.course.id = :courseId
            """)
    Page<CourseReview> findCourseReviewByCourseId(String courseId, Pageable pageable);
}
