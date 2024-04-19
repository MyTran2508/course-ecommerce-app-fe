package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends BaseRepository<Lecture> {

    @Query(value = "select section_id from lecture where id = :lectureId", nativeQuery = true)
    String getSectionIdByLectureId(String lectureId);
}
