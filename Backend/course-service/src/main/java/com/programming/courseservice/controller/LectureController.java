package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.error.exception.ResourceNotFoundException;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.util.ApiResources;
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.LectureDto;
import com.programming.courseservice.domain.dto.UserLogDto;
import com.programming.courseservice.domain.mapper.LectureMapper;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import com.programming.courseservice.domain.persistent.enumrate.ActionName;
import com.programming.courseservice.domain.persistent.enumrate.ActionObject;
import com.programming.courseservice.repository.LectureRepository;
import com.programming.courseservice.service.LectureService;
import com.programming.courseservice.service.UserLogService;
import com.programming.courseservice.utilities.annotation.ShowOpenAPI;
import com.programming.courseservice.utilities.communication.UserApi;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/lecture")
@RequiredArgsConstructor
public class LectureController extends BaseApiImpl<Lecture, LectureDto> {

    private final LectureService lectureService;

    private final LectureRepository lectureRepository;

    private final LectureMapper lectureMapper;

    private final UserLogService userLogService;

    private final UserApi userApi;

    @Override
    protected BaseService getBaseService() {
        return lectureService;
    }

    @Override
    public DataResponse<LectureDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<LectureDto> update(LectureDto objectDTO, String id) {
        lectureService.updateTotalDurationVideoLecturesForUpdate(objectDTO, id);

        Lecture savedLecture = lectureRepository.findById(id).orElse(null);
        if (savedLecture == null) {
            throw new ResourceNotFoundException(id + " does not exists in DB");
        }

        Lecture oldLectureClone = SerializationUtils.clone(savedLecture);
        System.out.println("prefix user: " + oldLectureClone);

        DataResponse<LectureDto> response = super.update(objectDTO, id);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey(id)
                .actionObject(ActionObject.LECTURE)
                .actionName(ActionName.UPDATE)
                .description(userLogService.writeUpdateLog(Lecture.class, oldLectureClone,
                        lectureMapper.dtoToEntity(response.getData()), true, 0))
                .build();

        userApi.addLog(userLogDto);

        return response;
    }

    @PostMapping(ApiResources.ADD + "/{sectionId}")
    @ShowOpenAPI
    public DataResponse<String> insert(@PathVariable("sectionId") String sectionId,
            @RequestBody LectureDto lectureDto) {

        DataResponse<String> response = lectureService.insert(sectionId, lectureDto);

        String stResult = response.getData();
        String lectureId = stResult.split(": ")[1].trim();
        Lecture entity = lectureRepository.findById(lectureId).orElse(null);

        // Add log
        UserLogDto userLogDto = UserLogDto.builder()
                .userName(SystemUtil.getCurrentUsername())
                .ip(SystemUtil.getUserIP())
                .actionKey(lectureId)
                .actionObject(ActionObject.LECTURE)
                .actionName(ActionName.CREATE)
                .description(userLogService.writePersistLog(Lecture.class, entity, true, 0))
                .build();
        System.out.println(userLogDto);
        userApi.addLog(userLogDto);

        return response;
    }

    @PutMapping("/update-list/{sectionId}")
    @ShowOpenAPI
    public DataResponse<String> updateList(@PathVariable("sectionId") String sectionId,
            @RequestBody List<LectureDto> lectureDtos) {

        return lectureService.updateList(sectionId, lectureDtos);
    }
}
