package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.SectionDto;
import com.programming.courseservice.domain.dto.UserLogDto;
import com.programming.courseservice.domain.mapper.SectionMapper;
import com.programming.courseservice.domain.persistent.entity.Section;
import com.programming.courseservice.domain.persistent.enumrate.ActionName;
import com.programming.courseservice.domain.persistent.enumrate.ActionObject;
import com.programming.courseservice.repository.CourseRepository;
import com.programming.courseservice.repository.SectionRepository;
import com.programming.courseservice.service.SectionService;
import com.programming.courseservice.service.UserLogService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import com.programming.courseservice.utilities.communication.UserApi;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/section")
public class SectionController extends BaseApiImpl<Section, SectionDto> {

    private final SectionService sectionService;

    private final SectionRepository sectionRepository;

    private final CourseRepository courseRepository;

    private final SectionMapper sectionMapper;

    private final UserLogService userLogService;

    private final UserApi userApi;

    @Override
    protected BaseService<Section, SectionDto> getBaseService() {
        return sectionService;
    }

    @Override
    @ShowOpenAPI
    public DataResponse<SectionDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<String> add(SectionDto objectDTO) {
        DataResponse<String> response =  super.add(objectDTO);

        String stResult = response.getData();
        String id = stResult.split(": ")[1].trim();
        Section entity = sectionRepository.findById(id).orElse(null);
        String courseId = courseRepository.findCourseIdByContentId(entity.getContent().getId());

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey("Course ID: " + courseId
                        + "; Content ID: " + entity.getContent().getId()
                        + "; Section ID: " + entity.getId())
                .actionObject(ActionObject.SECTION)
                .actionName(ActionName.CREATE)
                .description(userLogService.writePersistLog(Section.class, entity, true, 0))
                .build();

        userApi.addLog(userLogDto);

        return response;
    }

    @Override
    @ShowOpenAPI
    public DataResponse<SectionDto> update(SectionDto objectDTO, String id) {
        SectionDto sectionDtos = sectionService.updateSection(objectDTO);

        Section savedSection = sectionRepository.findById(id).orElse(null);
        if (savedSection == null) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }

        Section oldSectionClone = SerializationUtils.clone(savedSection);
        System.out.println("prefix user: " + oldSectionClone);
        DataResponse<SectionDto> response = super.update(sectionDtos, id);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey("Course ID: " + oldSectionClone.getContent().getCourse().getId()
                        + "; Content ID: " + objectDTO.getContent().getId()
                        + "; Section ID: " + id)
                .actionObject(ActionObject.SECTION)
                .actionName(ActionName.UPDATE)
                .description(userLogService.writeUpdateLog(Section.class, oldSectionClone, sectionMapper.dtoToEntity(response.getData()), true, 0))
                .build();

        userApi.addLog(userLogDto);

        return response;
    }

    @PutMapping("/update-list/{contentId}")
    @ShowOpenAPI
    public DataResponse<String> updateList(@RequestBody List<SectionDto> sectionDtoList,
            @PathVariable String contentId) {

        return sectionService.updateList(sectionDtoList, contentId);
    }

    @ShowOpenAPI
    @PostMapping("/upload")
    public DataResponse<List<String>> uploadFileSection(@RequestParam("files") MultipartFile[] files) {
        return sectionService.uploadFileSection(files);
    }

    @ShowOpenAPI
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> loadFile(@RequestParam("path") String path) {
        return sectionService.loadFile(path);
    }
}
