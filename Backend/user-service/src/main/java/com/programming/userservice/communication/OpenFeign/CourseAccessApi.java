package com.programming.userservice.communication.OpenFeign;

import com.main.progamming.common.response.DataResponse;
import com.programming.userservice.config.OpenFeignConfig;
import com.programming.userservice.domain.dto.CourseAccessListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "course-service", configuration = OpenFeignConfig.class)
public interface CourseAccessApi {

    @PostMapping("/api/courses/course-access/add-list")
    DataResponse<String> addList(@RequestBody CourseAccessListDto courseAccessListDto);
}
