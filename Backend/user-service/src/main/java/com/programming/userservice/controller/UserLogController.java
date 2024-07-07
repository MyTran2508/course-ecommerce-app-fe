package com.programming.userservice.controller;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.util.ApiResources;
import com.programming.userservice.domain.dto.UserLogDto;
import com.programming.userservice.domain.persistent.entity.UserLog;
import com.programming.userservice.service.UserLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/user-log")
public class UserLogController {

    private final UserLogService userLogService;

    @PostMapping("/add")
    public ResponseEntity<String> addLog(@RequestBody UserLogDto userLogDto) {

        UserLog userLog = new UserLog();
        BeanUtils.copyProperties(userLogDto, userLog);

        System.out.println("userLogDto: " + userLogDto);
        userLogService.addLog(userLog);

        return ResponseEntity.ok("OK");
    }

    @PostMapping("/filter")
    public ListResponse<List<UserLog>> filterUserLog(@RequestBody SearchKeywordDto searchKeywordDto) {
        return userLogService.filterUserLog(searchKeywordDto);
    }
}
