package com.programming.userservice.controller;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.util.ApiResources;
import com.programming.userservice.domain.persistent.entity.UserLog;
import com.programming.userservice.service.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/user-log")
public class UserLogController {

    private final UserLogService userLogService;

    @PostMapping(ApiResources.ADD)
    private DataResponse<String> addLog(@RequestBody UserLog userLog) {
        return userLogService.addLog(userLog);
    }

    @PostMapping("/filter")
    private ListResponse<List<UserLog>> filterUserLog(@RequestBody SearchKeywordDto searchKeywordDto) {
        return userLogService.filterUserLog(searchKeywordDto);
    }
}
