package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.LectureDto;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import com.programming.courseservice.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/lecture")
@RequiredArgsConstructor
public class LectureController extends BaseApiImpl<Lecture, LectureDto> {

    private final LectureService lectureService;

    @Override
    protected BaseService getBaseService() {
        return lectureService;
    }

    @Override
    public DataResponse<LectureDto> getById(String id) {
        return super.getById(id);
    }

    @Override
    public DataResponse<LectureDto> update(LectureDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @PostMapping("/insert/{sectionId}")
    public DataResponse<String> insert(@PathVariable("sectionId") String sectionId,
            @RequestBody LectureDto lectureDto) {

        return lectureService.insert(sectionId, lectureDto);
    }

    @PutMapping("/update-list/{sectionId}")
    public DataResponse<String> updateList(@RequestParam("sectionId") String sectionId,
            @RequestBody List<LectureDto> lectureDtos) {

        return lectureService.updateList(sectionId, lectureDtos);
    }
}
