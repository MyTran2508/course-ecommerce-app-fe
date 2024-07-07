package com.programming.courseservice.utilities.communication;

import com.main.progamming.common.response.DataResponse;
import com.programming.courseservice.domain.dto.AvatarDto;
import com.programming.courseservice.domain.dto.UserDto;
import com.programming.courseservice.domain.dto.UserLogDto;
import com.programming.courseservice.domain.persistent.entity.UserLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-service")
public interface UserApi {

    @GetMapping("/api/users/user/get-by-username/{username}")
    DataResponse<UserDto> getByUsername(@PathVariable String username);

    @GetMapping("/api/users/user/photos/{username}")
    ResponseEntity<AvatarDto> getAvatar(@PathVariable("username") String username);

    @PostMapping("/api/users/user-log/add")
    ResponseEntity<String> addLog(@RequestBody UserLogDto userLogDto);
}
