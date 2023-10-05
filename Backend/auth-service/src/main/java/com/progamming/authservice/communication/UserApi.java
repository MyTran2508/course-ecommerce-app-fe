package com.progamming.authservice.communication;

import com.main.progamming.common.response.DataResponse;
import com.progamming.authservice.entity.UserCredential;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserApi {
    @GetMapping("api/users/user/get-by-username/{username}")
    DataResponse<UserCredential> getUserByUserName(@PathVariable String username);
}
