package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.CommentReplyDto;
import com.programming.courseservice.domain.persistent.entity.CommentReply;
import com.programming.courseservice.service.CommentReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/comment-reply")
public class CommentReplyController extends BaseApiImpl<CommentReply, CommentReplyDto> {

    private final CommentReplyService commentReplyService;

    @Override
    protected BaseService<CommentReply, CommentReplyDto> getBaseService() {
        return commentReplyService;
    }
}
