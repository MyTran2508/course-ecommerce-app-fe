package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.ContentDto;
import com.programming.courseservice.domain.persistent.entity.Content;
import com.programming.courseservice.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/content")
public class ContentController extends BaseApiImpl<Content, ContentDto> {
    private final ContentService contentService;
    @Override
    protected BaseService<Content, ContentDto> getBaseService() {
        return contentService;
    }

    @Override
    public DataResponse<ContentDto> getById(String id) {
        return super.getById(id);
    }
}
