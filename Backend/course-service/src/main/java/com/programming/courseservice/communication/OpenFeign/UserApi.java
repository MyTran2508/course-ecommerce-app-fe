package com.programming.courseservice.communication.OpenFeign;

import com.main.progamming.common.response.DataResponse;
import com.programming.courseservice.domain.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserApi {
    @GetMapping("/api/users/user/get-by-username/{username}")
    DataResponse<UserDto> getByUsername(@PathVariable String username);
}
