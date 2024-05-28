package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.CommentReply;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository extends BaseRepository<CommentReply> {

    @Query("""
                SELECT cmtr FROM CommentReply cmtr
                WHERE cmtr.forumLecture.id = :formumLectureId
         """)
    Page<CommentReply> findByForumLectureId(String formumLectureId, Pageable pageable);
}
