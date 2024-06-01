package com.programming.courseservice.controller;

import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.programming.courseservice.domain.dto.ForumLectureDto;
import com.programming.courseservice.domain.mapper.ForumLectureMapper;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import com.programming.courseservice.repository.ForumLectureRepository;
import com.programming.courseservice.service.ForumLectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ForumLectureChatController {

    private final ForumLectureService forumLectureService;

    private final ForumLectureRepository forumLectureRepository;

    private final ForumLectureMapper forumLectureMapper;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/forum-lecture/add/{lectureId}")
    public void add(@Payload ForumLectureDto forumLectureDto, @DestinationVariable String lectureId) {
        DataResponse<String> response = forumLectureService.create(forumLectureDto);
        if (response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            String forumLectureId = response.getData().split("ID: ")[1];
            ForumLecture forumLecture = forumLectureRepository.findById(forumLectureId).get();

            ForumLectureDto responseDto = forumLectureMapper.entityToDto(forumLecture);
            responseDto.setRawAvatar(forumLectureDto.getRawAvatar());
            simpMessagingTemplate.convertAndSend("/rt/response/courses/forum-lecture/" + lectureId,
                    ResponseMapper.toDataResponseSuccess(responseDto));
        } else {
            simpMessagingTemplate.convertAndSend("/rt/response/courses/forum-lecture/" + lectureId,
                    ResponseMapper.toDataResponseSuccess(null));
        }
    }

    @MessageMapping("/forum-lecture/update/{lectureId}")
    public void update(@Payload ForumLectureDto forumLectureDto, @DestinationVariable String lectureId) {
        DataResponse<ForumLectureDto> response = forumLectureService.update(forumLectureDto.getId(), forumLectureDto);
        response.getData().setRawAvatar(forumLectureDto.getRawAvatar());
        simpMessagingTemplate.convertAndSend("/rt/response/courses/forum-lecture/" + lectureId, response);
    }
}
