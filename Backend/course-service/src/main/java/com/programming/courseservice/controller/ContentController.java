package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.AssignmentDto;
import com.programming.courseservice.domain.dto.ContentDto;
import com.programming.courseservice.domain.dto.UserLogDto;
import com.programming.courseservice.domain.mapper.ContentMapper;
import com.programming.courseservice.domain.persistent.entity.Assignment;
import com.programming.courseservice.domain.persistent.entity.Content;
import com.programming.courseservice.domain.persistent.enumrate.ActionName;
import com.programming.courseservice.domain.persistent.enumrate.ActionObject;
import com.programming.courseservice.repository.ContentRepository;
import com.programming.courseservice.service.ContentService;
import com.programming.courseservice.service.UserLogService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import com.programming.courseservice.utilities.communication.UserApi;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/content")
public class ContentController extends BaseApiImpl<Content, ContentDto> {

    private final ContentService contentService;

    private final ContentRepository contentRepository;

    private final ContentMapper contentMapper;

    private final UserLogService userLogService;

    private final UserApi userApi;

    @Override
    protected BaseService<Content, ContentDto> getBaseService() {
        return contentService;
    }

    @Override
    @ShowOpenAPI
    public DataResponse<ContentDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<ContentDto> update(ContentDto objectDTO, String id) {
        Content savedContent = contentRepository.findById(id).orElse(null);
        if (savedContent == null) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }

        Content oldContentClone = SerializationUtils.clone(savedContent);
        System.out.println("prefix user: " + oldContentClone);

        DataResponse<ContentDto> response = super.update(objectDTO, id);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey("Course ID: " + savedContent.getCourse().getId()
                        + "; Content ID: " + savedContent.getId())
                .actionObject(ActionObject.CONTENT)
                .actionName(ActionName.UPDATE)
                .description(userLogService.writeUpdateLog(Content.class, oldContentClone, contentMapper.dtoToEntity(response.getData()), true, 0))
                .build();

        userApi.addLog(userLogDto);

        return response;
    }

    @Override
    @ShowOpenAPI
    public DataResponse<String> add(ContentDto objectDTO) {
        DataResponse<String> response =  super.add(objectDTO);

        String stResult = response.getData();
        String id = stResult.split(": ")[1].trim();
        Content entity = contentRepository.findById(id).orElse(null);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey("Course ID: " + entity.getCourse().getId() + "; Content ID: " + entity.getId())
                .actionObject(ActionObject.CONTENT)
                .actionName(ActionName.CREATE)
                .description(userLogService.writePersistLog(Content.class, entity, true, 0))
                .build();

        userApi.addLog(userLogDto);

        return response;
    }

    @GetMapping("/get-by-course-id")
    @ShowOpenAPI
    public DataResponse<ContentDto> getByCourseId(@RequestParam("id") String courseId) {
        return contentService.getByCourseId(courseId);
    }
}
