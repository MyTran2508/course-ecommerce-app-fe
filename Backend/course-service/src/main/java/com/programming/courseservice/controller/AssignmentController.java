package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.AssignmentDto;
import com.programming.courseservice.domain.dto.UserLogDto;
import com.programming.courseservice.domain.mapper.AssignmentMapper;
import com.programming.courseservice.domain.persistent.entity.Assignment;
import com.programming.courseservice.domain.persistent.entity.UserLog;
import com.programming.courseservice.domain.persistent.enumrate.ActionName;
import com.programming.courseservice.domain.persistent.enumrate.ActionObject;
import com.programming.courseservice.repository.AssignmentRepository;
import com.programming.courseservice.service.AssignmentService;
import com.programming.courseservice.service.UserLogService;
import com.programming.courseservice.utilities.communication.UserApi;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/assignment")
public class AssignmentController extends BaseApiImpl<Assignment, AssignmentDto> {

    private final AssignmentService assignmentService;

    private final AssignmentRepository assignmentRepository;

    private final UserLogService userLogService;

    private final AssignmentMapper assignmentMapper;

    private final UserApi userApi;

    @Override
    protected BaseService<Assignment, AssignmentDto> getBaseService() {
        return assignmentService;
    }

    @Override
    public DataResponse<AssignmentDto> update(AssignmentDto objectDTO, String id) {
        Assignment savedAssignment = assignmentRepository.findById(id).orElse(null);
        if (savedAssignment == null) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }

        Assignment oldAssignmentClone = SerializationUtils.clone(savedAssignment);
        System.out.println("prefix user: " + oldAssignmentClone);

        DataResponse<AssignmentDto> response = super.update(objectDTO, id);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey("Lecture ID: " + savedAssignment.getLecture().getId() + "; Assignment ID: " + savedAssignment.getId())
                .actionObject(ActionObject.ASSIGNMENT)
                .actionName(ActionName.UPDATE)
                .description(userLogService.writeUpdateLog(Assignment.class, oldAssignmentClone, assignmentMapper.dtoToEntity(response.getData()), true, 0))
                .build();

        userApi.addLog(userLogDto);

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
        String id = stResult.split(": ")[1].trim();
        Assignment entity = assignmentRepository.findById(id).orElse(null);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey("Lecture ID: " + entity.getLecture().getId() + "; Assignment ID: " + entity.getId())
                .actionObject(ActionObject.ASSIGNMENT)
                .actionName(ActionName.CREATE)
                .description(userLogService.writePersistLog(Assignment.class, entity, true, 0))
                .build();

        userApi.addLog(userLogDto);

        return response;
    }
}
