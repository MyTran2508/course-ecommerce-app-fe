package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.ForumLectureDto;
import com.programming.courseservice.domain.mapper.ForumLectureMapper;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import com.programming.courseservice.repository.ForumLectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumLectureService extends BaseServiceImpl<ForumLecture, ForumLectureDto> {

    private final ForumLectureRepository forumLectureRepository;

    private final ForumLectureMapper forumLectureMapper;

    @Override
    protected BaseRepository<ForumLecture> getBaseRepository() {
        return forumLectureRepository;
    }

    @Override
    protected BaseMapper<ForumLecture, ForumLectureDto> getBaseMapper() {
        return forumLectureMapper;
    }

    @Override
    protected Page<ForumLectureDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        String lectureId = searchKeywordDto.getKeyword().get(0) == null ? null : searchKeywordDto.getKeyword().get(0).trim();

        return forumLectureRepository.findByLectureId(lectureId, pageable).map(forumLectureMapper::entityToDto);
    }

    @Override
    protected List<ForumLectureDto> getListSearchResults(String keyword) {
        return null;
    }
}
