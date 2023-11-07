package com.programming.courseservice.repository;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.dto.CourseDto;
import com.programming.courseservice.domain.persistent.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseRepository extends BaseRepository<Course> {
    @Query("select c from Course c where c.topic.id = :topicId")
    List<Course> getCourseByTopicId(@Param("topicId") String topicId, Sort sort);

    @Modifying
    @Query(value = "update courses set language_id=:languageId, topic_id=:topicId, level_id=:levelId where id=:id", nativeQuery = true)
    @Transactional
    void updateCourse(String id, String levelId, String topicId, String languageId);
}
