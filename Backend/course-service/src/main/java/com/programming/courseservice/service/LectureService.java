package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.LectureDto;
import com.programming.courseservice.domain.mapper.LectureMapper;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import com.programming.courseservice.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService extends BaseServiceImpl<Lecture, LectureDto> {
    private final LectureRepository lectureRepository;
    private final LectureMapper lectureMapper;
    @Override
    protected BaseRepository<Lecture> getBaseRepository() {
        return lectureRepository;
    }

    @Override
    protected BaseMapper<Lecture, LectureDto> getBaseMapper() {
        return lectureMapper;
    }

    @Override
    protected Page<LectureDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<LectureDto> getListSearchResults(String keyword) {
        return null;
    }
}
