package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.CourseDto;
import com.programming.courseservice.domain.dto.SearchCourseDto;
import com.programming.courseservice.domain.persistent.entity.Course;
import com.programming.courseservice.service.CourseService;
import com.programming.courseservice.util.annotation.ShowOpenAPI;
import com.programming.courseservice.util.constant.S3Constrant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @ShowOpenAPI
    public DataResponse<CourseDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    @ShowOpenAPI
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public DataResponse<CourseDto> add(CourseDto objectDTO) {
        return super.add(objectDTO);
    }


    @ShowOpenAPI
    @PostMapping("/images")
    public DataResponse<String> uploadCourseImage(@RequestParam("file") MultipartFile file) {
        return courseService.uploadCourseImage(file);
    }

    @ShowOpenAPI
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> loadFile(@RequestParam("path") String path) {
        return courseService.loadFile(path);
    }

    @ShowOpenAPI
    @PostMapping("/videos")
    public DataResponse<String> uploadCourseVideo(@RequestParam("file") MultipartFile file) {
        return courseService.uploadCourseVideo(file);
    }

    @Override
    @ShowOpenAPI
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public DataResponse<CourseDto> update(CourseDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @GetMapping("/newest/{topic-id}/{size}")
    @ShowOpenAPI
    public ListResponse<List<CourseDto>> getNewestCourse(@PathVariable("topic-id") String topicId, @PathVariable("size") Integer size) {
        return courseService.getNewestCourse(topicId, size);
    }

    @GetMapping("/popular/{topic-id}/{size}")
    public ListResponse<List<CourseDto>> getPopularCourse(@PathVariable("topic-id") String topicId, @PathVariable("size") Integer size) {
        return courseService.getPopularCourse(topicId, size);
    }

    @PostMapping("/filter")
    public ListResponse<CourseDto> getFiltedCourse(@RequestBody SearchCourseDto searchCourseDto) {
        return courseService.getFiltedCourse(searchCourseDto);
    }

    @GetMapping("/get-all-by-user-id")
    public ListResponse<CourseDto> getAllCourseProgressByUserId(@RequestParam("userId") String userId) {
        return courseService.getCourseAccessByUserId(userId);
    }
}
