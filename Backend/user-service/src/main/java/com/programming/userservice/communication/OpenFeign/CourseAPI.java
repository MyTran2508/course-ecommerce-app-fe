package com.programming.userservice.communication.OpenFeign;

import com.main.progamming.common.response.DataResponse;
import com.programming.userservice.config.OpenFeignConfig;
import com.programming.userservice.domain.dto.CourseProgressListDto;
import com.programming.userservice.domain.dto.StatisticsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "course-service", configuration = OpenFeignConfig.class)
public interface CourseAPI {

    @PostMapping("/api/courses/course-progress/add-list")
    DataResponse<String> addList(@RequestBody CourseProgressListDto courseProgressListDto);

    @PostMapping("api/courses/course-progress/get-total-register-course")
    DataResponse<Integer> getTotalRegisteredCourseByYearAndMonth(@RequestBody StatisticsRequest statisticsRequest);

    @PostMapping("api/courses/course/get-total-approved-course")
    DataResponse<Integer> getTotalApprovedCourseByYearAndMonth(@RequestBody StatisticsRequest statisticsRequest);
}
