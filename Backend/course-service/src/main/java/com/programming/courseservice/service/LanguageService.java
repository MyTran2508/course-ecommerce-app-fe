package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.LanguageDto;
import com.programming.courseservice.domain.mapper.LanguageMapper;
import com.programming.courseservice.domain.persistent.entity.Language;
import com.programming.courseservice.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageService extends BaseServiceImpl<Language, LanguageDto> {
    private final LanguageRepository languageRepository;

    private final LanguageMapper languageMapper;

    @Override
    protected BaseRepository<Language> getBaseRepository() {
        return languageRepository;
    }

    @Override
    protected BaseMapper<Language, LanguageDto> getBaseMapper() {
        return languageMapper;
    }

    @Override
    protected Page<LanguageDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<LanguageDto> getListSearchResults(String keyword) {
        return null;
    }
}
