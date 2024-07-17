package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumLectureRepository extends BaseRepository<ForumLecture> {

    @Query("""
                SELECT fl FROM ForumLecture fl
                WHERE fl.lectureId = :lectureId
                order by (fl.countReplyComment + fl.likeAmount - fl.disLikeAmount) desc
         """)
    Page<ForumLecture> findByLectureId(String lectureId, Pageable pageable);


}
