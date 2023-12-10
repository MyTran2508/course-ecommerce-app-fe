package com.programming.courseservice.repository;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.dto.CourseDto;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.domain.persistent.entity.Language;
import com.programming.courseservice.domain.persistent.entity.Level;
import com.programming.courseservice.domain.persistent.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseRepository extends BaseRepository<Course> {
    @Query("select c from Course c where c.topic.id = :topicId and c.isApproved = true")
    List<Course> getCourseByTopicId(@Param("topicId") String topicId, Pageable pageable);

    @Modifying
    @Query(value = "update course set language_id=:languageId, topic_id=:topicId, level_id=:levelId where id=:id", nativeQuery = true)
    @Transactional
    void updateCourse(String id, String levelId, String topicId, String languageId);

    @Query("""
                Select cg.course from CourseProgress cg where cg.course.topic.id = :topicId and cg.course.isApproved = true
                GROUP BY cg.course.id 
                order by count(cg.course.id) DESC
           """)
    List<Course> findPopularCourses(@Param("topicId") String topicId, Pageable pageable);

    @Query("""
                Select c from Course c where c.level.id IN :levelIds
                and c.language.id IN :languageIds
                and c.topic.id IN :topicIds
                and (c.name LIKE %:keyword% OR c.subTitle LIKE %:keyword% OR :keyword IS NULL)
                and c.isApproved = true
            """)
    Page<Course> filterCourse(@Param("levelIds") List<String> levelIds,
                              @Param("languageIds") List<String> languageIds,
                              @Param("topicIds") List<String> topicIds,
                              @Param("keyword") String keyword,
                              Pageable pageable);

    @Query("select c from Course c, CourseProgress as cg where cg.userId = :userId and c.id = cg.course.id and cg.course.isApproved = true")
    Page<Course> getCourseAccessByUserId(@Param("userId") String topicId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("Update Course c set c.isApproved = :isApproved WHERE c.id = :id")
    void updateIsApproved(String id, boolean isApproved);

//    @Transactional
//    @Modifying
//    @Query("Update Course c set c.isAwaitingApproval = :awaitingApproval WHERE c.id = :id")
//    void updateAwaitingApproval(String id, boolean awaitingApproval);

    @Query("""
                select c from Course c where (c.name LIKE %:name% or c.subTitle LIKE %:name% or :name IS NULL)
                and (c.isApproved = :isApproved or :isApproved IS NULL)
                and (c.isAwaitingApproval = :isAwaitingApproval or :isAwaitingApproval IS NULL)
                and c.isCompletedContent = true
            """)
    Page<Course> searchCourseOfAdmin(@Param("name") String name, @Param("isApproved") Boolean isApproved,
                                     @Param("isAwaitingApproval") Boolean isAwaitingApproval, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM course WHERE YEAR(FROM_UNIXTIME(created / 1000)) = :targetYear " +
            "AND (MONTH(FROM_UNIXTIME(created / 1000)) = :targetMonth OR :targetMonth IS NULL) " +
            "AND is_approved = true", nativeQuery = true)
    Integer getTotalApprovedCourseByYearAndMonth(int targetYear, Integer targetMonth);
}
