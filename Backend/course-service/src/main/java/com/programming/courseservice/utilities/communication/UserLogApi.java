package com.programming.courseservice.utilities.communication;

import com.programming.courseservice.domain.persistent.entity.UserLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service")
public interface UserLogApi {

    @PostMapping("/api/users/user-log/add")
    ResponseEntity<String> addUserLog(@RequestBody UserLog userLog);
}
