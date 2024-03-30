package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.LanguageDto;
import com.programming.courseservice.domain.persistent.entity.Language;
import com.programming.courseservice.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses/language")
public class LanguageController extends BaseApiImpl<Language, LanguageDto> {

    private final LanguageService languageService;

    @Override
    protected BaseService<Language, LanguageDto> getBaseService() {
        return languageService;
    }

    @Override
    public DataResponse<String> add(LanguageDto objectDTO) {
        return super.add(objectDTO);
    }

    @Override
    public DataResponse<LanguageDto> update(LanguageDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @Override
    public ListResponse<LanguageDto> getAll() {
        List<LanguageDto> languageDto = super.getAll().getData();
        return ResponseMapper.toListResponseSuccess(languageDto.stream()
                .sorted(Comparator.comparing(LanguageDto::getName))
                .collect(Collectors.toList()));
    }

    @Override
    public DataResponse<LanguageDto> getById(String id) {
        return super.getById(id);
    }
}
