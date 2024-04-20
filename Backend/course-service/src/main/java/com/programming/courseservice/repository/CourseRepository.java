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
    @Query("SELECT c FROM Course c where c.isApproved = true")
    List<Course> getAllCourseIsApproved();

    @Query("SELECT c FROM Course c WHERE (c.topic.id = :topicId OR :topicId IS NULL) AND c.isApproved = true")
    List<Course> getCourseByTopicId(@Param("topicId") String topicId, Pageable pageable);

    @Modifying
    @Query(value = "update course set language_id=:languageId, topic_id=:topicId, level_id=:levelId where id=:id", nativeQuery = true)
    @Transactional
    void updateCourse(String id, String levelId, String topicId, String languageId);

    @Query("""
                SELECT c FROM Course c LEFT JOIN CourseProgress cg
                ON cg.course.id = c.id
                WHERE (c.topic.id = :topicId OR :topicId IS NULL)
                AND c.isApproved = true
                GROUP BY c.id
                ORDER BY COUNT(c.id) DESC
           """)
    List<Course> findPopularCourses(@Param("topicId") String topicId, Pageable pageable);

    @Query("""
                SELECT c FROM Course c
                WHERE c.level.id IN :levelIds
                AND c.language.id IN :languageIds
                AND c.topic.id IN :topicIds
                AND ((:isFree = false AND c.price > 0) OR (:isFree = true AND c.price = 0) OR (:isFree IS NULL))
                AND (c.name LIKE %:keyword% OR c.subTitle LIKE %:keyword% OR :keyword IS NULL)
                AND (c.averageRating >= :minRatingValue OR :minRatingValue IS NULL)
                AND c.isApproved = true
                ORDER BY ((c.totalRatings * 0.6) + (c.averageRating * 0.4)) DESC
            """)
    Page<Course> filterCourse(@Param("levelIds") List<String> levelIds,
                              @Param("languageIds") List<String> languageIds,
                              @Param("topicIds") List<String> topicIds,
                              @Param("isFree") Boolean isFree,
                              @Param("minRatingValue") Float minRatingValue,
                              @Param("keyword") String keyword,
                              Pageable pageable);

    @Query("""
                SELECT c FROM Course c LEFT JOIN CourseProgress cg
                ON cg.course.id = c.id
                WHERE c.level.id IN :levelIds
                AND c.language.id IN :languageIds
                AND c.topic.id IN :topicIds
                AND ((:isFree = false AND c.price > 0) OR (:isFree = true AND c.price = 0) OR (:isFree IS NULL))
                AND (c.name LIKE %:keyword% OR c.subTitle LIKE %:keyword% OR :keyword IS NULL)
                AND (c.averageRating >= :minRatingValue OR :ratingValue IS NULL)
                GROUP BY c.id
                ORDER BY COUNT(c.id) DESC
            """)
    Page<Course> filterCoursePopular(@Param("levelIds") List<String> levelIds,
                              @Param("languageIds") List<String> languageIds,
                              @Param("topicIds") List<String> topicIds,
                              @Param("isFree") Boolean isFree,
                              @Param("keyword") String keyword,
                              @Param("minRatingValue") Float minRatingValue,
                              Pageable pageable);
    @Query("select c from Course c, CourseProgress as cg where cg.userId = :userId and c.id = cg.course.id and cg.course.isApproved = true")
    Page<Course> getCourseAccessByUserId(@Param("userId") String userId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("Update Course c set c.isApproved = :isApproved WHERE c.id = :id")
    void updateIsApproved(String id, boolean isApproved);

    @Query("""
                SELECT c FROM Course c
                WHERE (c.name LIKE %:name% OR c.subTitle LIKE %:name% OR :name IS NULL)
                AND (c.creator = :creator OR :creator IS NULL)
                AND (c.isApproved = :isApproved or :isApproved IS NULL)
                AND (c.isAwaitingApproval = :isAwaitingApproval OR :isAwaitingApproval IS NULL)
                AND (c.isCompletedContent = :isCompletedContent OR :isCompletedContent IS NULL)
            """)
    Page<Course> searchCourseOfAdmin(String name, String creator, Boolean isApproved,
                                     Boolean isAwaitingApproval, Boolean isCompletedContent, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM course WHERE YEAR(FROM_UNIXTIME(created / 1000)) = :targetYear " +
            "AND (MONTH(FROM_UNIXTIME(created / 1000)) = :targetMonth OR :targetMonth IS NULL) " +
            "AND is_approved = true", nativeQuery = true)
    Integer getTotalApprovedCourseByYearAndMonth(int targetYear, Integer targetMonth);

    @Query(value = "SELECT c.topic_id, SUM(c.price) FROM course_progress as cp " +
            "INNER JOIN course as c ON cp.course_id = c.id " +
            "WHERE YEAR(FROM_UNIXTIME(cp.created / 1000)) = :targetYear " +
            "GROUP BY c.topic_id", nativeQuery = true)
    List<Object[]> getMonthlySalesByTopics(@Param("targetYear") int targetYear);
}
