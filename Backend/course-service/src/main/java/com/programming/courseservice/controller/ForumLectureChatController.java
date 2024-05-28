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
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ForumLectureChatController {

    private final ForumLectureService forumLectureService;

    private final ForumLectureRepository forumLectureRepository;

    private final ForumLectureMapper forumLectureMapper;

    @MessageMapping("/forum-lecture/add") // request /rt/request/courses/forum-lecture/add
    @SendTo("/rt/response/courses/forum-lecture") // response /rt/response/courses/forum-lecture
    public DataResponse<ForumLectureDto> add(@Payload ForumLectureDto forumLectureDto) {
        DataResponse<String> response = forumLectureService.create(forumLectureDto);
        if (response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            String forumLectureId = response.getData().split("ID: ")[1];
            System.out.println(forumLectureId);
            ForumLecture forumLecture = forumLectureRepository.findById(forumLectureId).get();
            return ResponseMapper.toDataResponseSuccess(forumLectureMapper.entityToDto(forumLecture));
        }
        return ResponseMapper.toDataResponseSuccess(null);
    }

    @MessageMapping("/forum-lecture/update")
    @SendTo("/rt/response/courses/forum-lecture")
    public DataResponse<ForumLectureDto> update(@Payload ForumLectureDto forumLectureDto) {
        return forumLectureService.update(forumLectureDto.getId(), forumLectureDto);
    }
}
