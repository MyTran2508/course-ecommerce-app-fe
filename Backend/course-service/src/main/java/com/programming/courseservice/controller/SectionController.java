package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.SectionDto;
import com.programming.courseservice.domain.persistent.entity.Section;
import com.programming.courseservice.service.SectionService;
import com.programming.courseservice.util.annotation.ShowOpenAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/section")
public class SectionController extends BaseApiImpl<Section, SectionDto> {
    private final SectionService sectionService;
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
    public DataResponse<SectionDto> add(SectionDto objectDTO) {
        return super.add(objectDTO);
    }

    @Override
    @ShowOpenAPI
    public DataResponse<SectionDto> update(SectionDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @PostMapping("/upload")
    @ShowOpenAPI
    public DataResponse<List<String>> uploadFileSection(@RequestParam("files") MultipartFile[] files) {
        return sectionService.uploadFileSection(files);
    }

    @ShowOpenAPI
    @GetMapping("/download")
    public ResponseEntity<ByteArrayResource> loadFile(@RequestParam("path") String path) {
        return sectionService.loadFile(path);
    }
}