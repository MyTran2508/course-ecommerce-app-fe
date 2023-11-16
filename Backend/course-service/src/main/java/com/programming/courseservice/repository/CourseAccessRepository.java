package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.CourseAccess;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CourseAccessRepository extends BaseRepository<CourseAccess> {
    @Query("Select ca from CourseAccess ca where ca.userId = :userId and ca.course.id = :courseId")
    Optional<CourseAccess> findByUserIdAndCourseId(String userId, String courseId);

    @Query(value = "Insert into course_access(user_id, course_id) VALUES(:user_id, :course_id)", nativeQuery = true)
    @Modifying
    @Transactional
    void insertCourseAccess(@Param("user_id") String userId, @Param("course_id") String courseId);
}
