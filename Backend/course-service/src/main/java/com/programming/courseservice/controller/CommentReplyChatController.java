package com.programming.courseservice.controller;

import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.programming.courseservice.domain.dto.CommentReplyDto;
import com.programming.courseservice.domain.dto.ForumLectureDto;
import com.programming.courseservice.domain.mapper.CommentReplyMapper;
import com.programming.courseservice.domain.persistent.entity.CommentReply;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import com.programming.courseservice.repository.CommentReplyRepository;
import com.programming.courseservice.service.CommentReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentReplyChatController {

    private final CommentReplyService commentReplyService;

    private final CommentReplyRepository commentReplyRepository;

    private final CommentReplyMapper commentReplyMapper;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/comment-reply/add/{lectureId}/{forumLectureId}")
    public void add(
            @Payload CommentReplyDto commentReplyDto,
            @DestinationVariable String lectureId,
            @DestinationVariable String forumLectureId
    ) {
        DataResponse<String> response = commentReplyService.insert(forumLectureId, commentReplyDto);
        if (response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            String commentReplyId = response.getData().split("ID: ")[1];
            System.out.println(commentReplyId);
            CommentReply commentReply = commentReplyRepository.findById(commentReplyId).get();
            CommentReplyDto responseDto = commentReplyMapper.entityToDto(commentReply);
            responseDto.setForumLectureId(forumLectureId);
            simpMessagingTemplate.convertAndSend("/rt/response/courses/forum-lecture/" + lectureId,
                    ResponseMapper.toDataResponseSuccess(responseDto));
        } else {
            simpMessagingTemplate.convertAndSend("/rt/response/courses/forum-lecture/" + lectureId,
                    ResponseMapper.toDataResponseSuccess(null));
        }
    }

    @MessageMapping("/comment-reply/update/{lectureId}/{forumLectureId}")
    public void update(
            @Payload CommentReplyDto commentReplyDto,
            @DestinationVariable String lectureId,
            @DestinationVariable String forumLectureId
    ) {
        DataResponse<CommentReplyDto> response = commentReplyService.update(commentReplyDto.getId(), commentReplyDto);
        response.getData().setForumLectureId(forumLectureId);
        simpMessagingTemplate.convertAndSend("/rt/response/courses/forum-lecture/" + lectureId, response);
    }
}
