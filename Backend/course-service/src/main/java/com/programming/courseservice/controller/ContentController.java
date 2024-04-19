package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.ContentDto;
import com.programming.courseservice.domain.persistent.entity.Content;
import com.programming.courseservice.service.ContentService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    @ShowOpenAPI
    public DataResponse<ContentDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<ContentDto> update(ContentDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<String> add(ContentDto objectDTO) {
        return super.add(objectDTO);
    }

    @GetMapping("/get-by-course-id")
    @ShowOpenAPI
    public DataResponse<ContentDto> getByCourseId(@RequestParam("id") String courseId) {
        return contentService.getByCourseId(courseId);
    }
}
