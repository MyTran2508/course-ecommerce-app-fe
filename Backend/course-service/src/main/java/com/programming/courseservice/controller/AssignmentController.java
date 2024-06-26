package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.AssignmentDto;
import com.programming.courseservice.domain.mapper.AssignmentMapper;
import com.programming.courseservice.domain.persistent.entity.Assignment;
import com.programming.courseservice.domain.persistent.entity.UserLog;
import com.programming.courseservice.domain.persistent.enumrate.ActionName;
import com.programming.courseservice.domain.persistent.enumrate.ActionObject;
import com.programming.courseservice.repository.AssignmentRepository;
import com.programming.courseservice.service.AssignmentService;
import com.programming.courseservice.service.UserLogService;
import com.programming.courseservice.utilities.communication.UserLogApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/assignment")
public class AssignmentController extends BaseApiImpl<Assignment, AssignmentDto> {

    private final AssignmentService assignmentService;

    private final AssignmentRepository assignmentRepository;

    private final UserLogService userLogService;

    private final AssignmentMapper assignmentMapper;

    private final UserLogApi userLogApi;

    @Override
    protected BaseService<Assignment, AssignmentDto> getBaseService() {
        return assignmentService;
    }

    @Override
    public DataResponse<AssignmentDto> update(AssignmentDto objectDTO, String id) {
        Optional<Assignment> optionalAssignment = assignmentRepository.findById(id);
        if (optionalAssignment.isEmpty()) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }
        Assignment assignment = optionalAssignment.get();

        DataResponse<AssignmentDto> response = super.update(objectDTO, id);

        // Add log
        UserLog userLog = UserLog.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey(id)
                .actionObject(ActionObject.ASSIGNMENT)
                .actionName(ActionName.UPDATE)
                .description(userLogService.writeUpdateLog(Assignment.class, assignment, assignmentMapper.dtoToEntity(response.getData()), true, 0))
                .build();

        userLogApi.addUserLog(userLog);

        return response;
    }

    @Override
    public DataResponse<AssignmentDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    public DataResponse<String> add(AssignmentDto objectDTO) {

        DataResponse<String> response =  super.add(objectDTO);

        String stResult = response.getData();
        String userId = stResult.split(": ")[1].trim();
        Assignment entity = assignmentRepository.findById(userId).orElse(null);

        // Add log
        UserLog userLog = UserLog.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey(userId)
                .actionObject(ActionObject.ASSIGNMENT)
                .actionName(ActionName.CREATE)
                .description(userLogService.writePersistLog(Assignment.class, entity, true, 0))
                .build();

        userLogApi.addUserLog(userLog);

        return response;
    }
}
