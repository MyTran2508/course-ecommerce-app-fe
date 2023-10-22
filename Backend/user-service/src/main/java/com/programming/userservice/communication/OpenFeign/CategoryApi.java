package com.programming.userservice.communication.OpenFeign;

import com.main.progamming.common.response.DataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "course-service")
public interface CategoryApi {
}
