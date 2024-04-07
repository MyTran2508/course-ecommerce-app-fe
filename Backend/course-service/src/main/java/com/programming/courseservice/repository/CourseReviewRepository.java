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
                ORDER BY (cr.likeAmount - cr.disLikeAmount) * (cr.likeAmount / (cr.likeAmount + cr.disLikeAmount))
                 + (cr.rating * (1 + ((cr.likeAmount - cr.disLikeAmount) * (cr.likeAmount / (cr.likeAmount + cr.disLikeAmount))) / 20))
                 + (1 / ((:currentTime - cr.created) / (1000 * 60 * 60 * 24) + 1)) * 100  DESC
           """)
    Page<CourseReview> findCourseReviewByCourseId(String courseId, Long currentTime, Pageable pageable);

    CourseReview findCourseReviewsByUsername(String username);

    @Query("""
                SELECT cr FROM CourseReview cr
                WHERE cr.username = :username
                AND cr.course.id = :courseId
          """)
    CourseReview findCourseReviewsByUsernameAndCourseId(String username, String courseId);
}
