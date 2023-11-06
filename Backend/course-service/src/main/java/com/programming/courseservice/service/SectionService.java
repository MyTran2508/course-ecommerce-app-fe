package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseService;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.SectionDto;
import com.programming.courseservice.domain.mapper.SectionMapper;
import com.programming.courseservice.domain.persistent.entity.Section;
import com.programming.courseservice.repository.SectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService extends BaseServiceImpl<Section, SectionDto> {
    private final SectionRepository sectionRepository;
    private final SectionMapper sectionMapper;
    @Override
    protected BaseRepository<Section> getBaseRepository() {
        return sectionRepository;
    }

    @Override
    protected BaseMapper<Section, SectionDto> getBaseMapper() {
        return sectionMapper;
    }

    @Override
    protected Page<SectionDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<SectionDto> getListSearchResults(String keyword) {
        return null;
    }
}
