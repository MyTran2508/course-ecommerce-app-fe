package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.message.StatusMessage;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.AssignmentDto;
import com.programming.courseservice.domain.mapper.AssignmentMapper;
import com.programming.courseservice.domain.persistent.entity.Assignment;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import com.programming.courseservice.repository.AssignmentRepository;
import com.programming.courseservice.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService extends BaseServiceImpl<Assignment, AssignmentDto> {

    private final AssignmentRepository assignmentRepository;

    private final AssignmentMapper assignmentMapper;

    @Override
    protected BaseRepository<Assignment> getBaseRepository() {
        return assignmentRepository;
    }

    @Override
    protected BaseMapper<Assignment, AssignmentDto> getBaseMapper() {
        return assignmentMapper;
    }

    @Override
    protected Page<AssignmentDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<AssignmentDto> getListSearchResults(String keyword) {
        return null;
    }
}
