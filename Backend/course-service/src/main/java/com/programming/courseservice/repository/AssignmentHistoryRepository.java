package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.AssignmentHistory;
import org.springframework.data.jpa.repository.Query;
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
}
