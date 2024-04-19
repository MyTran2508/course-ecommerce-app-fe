package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.TopicDto;
import com.programming.courseservice.domain.persistent.entity.Topic;
import com.programming.courseservice.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/topic")
public class TopicController extends BaseApiImpl<Topic, TopicDto> {

    private final TopicService topicService;

    @Override
    protected BaseService<Topic, TopicDto> getBaseService() {
        return topicService;
    }

    @Override
    public ListResponse<TopicDto> getAll() {
        return super.getAll();
    }
}
