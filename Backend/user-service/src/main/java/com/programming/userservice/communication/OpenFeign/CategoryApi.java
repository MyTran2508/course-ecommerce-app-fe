package com.programming.userservice.communication.OpenFeign;

import com.programming.userservice.core.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "http://localhost:8081", value = "course-service")
public interface CategoryApi {
    @GetMapping("api/category/get-all")
    CategoryDto getAllCategory();
}
