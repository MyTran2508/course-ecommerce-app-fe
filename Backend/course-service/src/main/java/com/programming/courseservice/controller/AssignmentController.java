package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.AssignmentDto;
import com.programming.courseservice.domain.persistent.entity.Assignment;
import com.programming.courseservice.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/assignment")
public class AssignmentController extends BaseApiImpl<Assignment, AssignmentDto> {

    private final AssignmentService assignmentService;

    @Override
    protected BaseService<Assignment, AssignmentDto> getBaseService() {
        return assignmentService;
    }

    @Override
    public DataResponse<AssignmentDto> update(AssignmentDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @Override
    public DataResponse<AssignmentDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    public DataResponse<String> add(AssignmentDto objectDTO) {
        return super.add(objectDTO);
    }
}
