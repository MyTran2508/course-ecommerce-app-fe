package com.programming.courseservice.repository;

import com.main.progamming.common.repository.BaseRepository;
import com.programming.courseservice.domain.persistent.entity.CommentReply;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository extends BaseRepository<CommentReply> {
}
