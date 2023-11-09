package com.programming.userservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.userservice.domain.persistent.entity.CourseAccess;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseAccessRepository extends BaseRepository<CourseAccess> {
    @Query("Select ca from CourseAccess ca where ca.user.id = :userId and ca.courseId = :courseId")
    Optional<CourseAccess> findByUserIdAndCourseId(String userId, String courseId);
}
