package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.AssignmentHistoryDto;
import com.programming.courseservice.domain.persistent.entity.AssignmentHistory;
import com.programming.courseservice.service.AssignmentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/assignment-history")
public class AssignmentHistoryController extends BaseApiImpl<AssignmentHistory, AssignmentHistoryDto> {

    private final AssignmentHistoryService assignmentHistoryService;

    @Override
    protected BaseService<AssignmentHistory, AssignmentHistoryDto> getBaseService() {
        return assignmentHistoryService;
    }

    public DataResponse<String> add(AssignmentHistoryDto objectDTO) {
        return super.add(objectDTO);
    }

    @Override
    public DataResponse<AssignmentHistoryDto> update(AssignmentHistoryDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @Override
    public DataResponse<AssignmentHistoryDto> getById(String id) {
        return super.getById(id);
    }

    @GetMapping("/get-by-username")
    public ListResponse<AssignmentHistoryDto> getByUsername(
            @RequestParam("username") String username
    ) {
        return assignmentHistoryService.getByUsername(username);
    }

    @GetMapping("/get-by-username-and-lecture-id")
    public ListResponse<AssignmentHistoryDto> getByUsernameAndLectureId(
            @RequestParam("username") String username,
            @RequestParam("lectureId") String lectureId
    ) {
        return assignmentHistoryService.getByUsernameAndLectureId(username, lectureId);
    }
}
