package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.CourseDto;
import com.programming.courseservice.domain.dto.SearchCourseDto;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/course")
public class CourseController extends BaseApiImpl<Course, CourseDto> {
    private final CourseService courseService;

    @Override
    protected BaseService<Course, CourseDto> getBaseService() {
        return courseService;
    }

    @Override
    public DataResponse<CourseDto> getById(String id) {
        return super.getById(id);
    }


    @GetMapping("/newest/{topic-id}/{size}")
    public ListResponse<List<CourseDto>> getNewestCourse(@PathVariable("topic-id") String topicId, @PathVariable("size") Integer size) {
        return courseService.getNewestCourse(topicId, size);
    }

    @GetMapping("/popular/{topic-id}/{size}")
    public ListResponse<CourseDto> getPopularCourse(@PathVariable("topic-id") String topicId, @PathVariable("size") Integer size) {
        return courseService.getPopularCourse(topicId, size);
    }

    @GetMapping("/favorites/{topic-id}/{size}")
    public ListResponse<CourseDto> getFavoritesCourse(@PathVariable("topic-id") String topicId, @PathVariable("size") Integer size) {
        return courseService.getFavoritesCourse(topicId, size);
    }

    @PostMapping("/filter")
    public ListResponse<CourseDto> getFiltedCourse(@RequestBody SearchCourseDto searchCourseDto) {
        return courseService.getFiltedCourse(searchCourseDto);
    }

}
