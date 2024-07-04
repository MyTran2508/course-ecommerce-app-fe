package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.AssignmentHistory;
import com.programming.courseservice.domain.persistent.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentHistoryRepository extends BaseRepository<AssignmentHistory> {

    List<AssignmentHistory> findByUsername(String username);

    @Query("""
        SELECT ah
        FROM AssignmentHistory ah
        WHERE ah.username = :username
        AND ah.assignment.lecture.id = :lectureId
        ORDER BY ah.originalNumber DESC
    """)
    List<AssignmentHistory> findByUsernameAndLectureId(String username, String lectureId);

    @Query("""
        SELECT MAX(ah.originalNumber)
        FROM AssignmentHistory ah
        WHERE ah.username = :username
        AND ah.assignment.lecture.id = :lectureId
    """)
    Integer getMaxOriginalNumber(String username, String lectureId);

    @Query("""
        SELECT ah
        FROM AssignmentHistory ah
        WHERE ah.username = :username
        AND ah.assignment.lecture.id = :lectureId
        AND ah.originalNumber = :originalNumber
    """)
    Optional<AssignmentHistory> findByUsernameAndLectureIdAndOriginalNumber(
            String username, String lectureId, Integer originalNumber
    );

    @Query(
            """
                SELECT DISTINCT ah.username
                FROM AssignmentHistory ah
                WHERE ah.assignment.creator = :creator
                AND ah.username LIKE %:username%
            """
    )
    List<String> getKeywordUsername(
            @Param("creator") String creator,
            @Param("username") String username
    );

    @Query(
            """
                SELECT DISTINCT ah.assignment.lecture.name
                FROM AssignmentHistory ah
                WHERE ah.assignment.creator = :creator
                AND ah.assignment.lecture.name LIKE %:lectureName%
            """
    )
    List<String> getKeywordLectureName(
            @Param("creator") String creator,
            @Param("lectureName") String lectureName
    );

    @Query(
            """
                SELECT ah FROM AssignmentHistory ah
                WHERE (
                    (:isEmptySearchChooseList = true AND :isNullAllSearchKeywordDto = true) OR
                    ((
                        (ah.username IN :usernameList OR ah.assignment.lecture.name IN :lectureNameList)
                    ) OR (
                        (:isNullAllSearchKeywordDto = false) AND
                        (:likeUsername IS NULL OR ah.username LIKE %:likeUsername%) AND
                        (:likeLectureName IS NULL OR ah.assignment.lecture.name LIKE %:likeLectureName%)
                    ))
                )
            """
    )
    Page<AssignmentHistory> seaarchAssignmentHistory(
            @Param("isEmptySearchChooseList") boolean isEmptySearchChooseList,
            @Param("usernameList") List<String> usernameList,
            @Param("lectureNameList") List<String> lectureNameList,
            @Param("isNullAllSearchKeywordDto") boolean isNullAllSearchKeywordDto,
            @Param("likeUsername") String likeUsername,
            @Param("likeLectureName") String likeLectureName,
            Pageable pageable
    );
}
