package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.AssignmentHistoryDto;
import com.programming.courseservice.domain.mapper.AssignmentHistoryMapper;
import com.programming.courseservice.domain.persistent.entity.AssignmentHistory;
import com.programming.courseservice.repository.AssignmentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentHistoryService extends BaseServiceImpl<AssignmentHistory, AssignmentHistoryDto> {

    private final AssignmentHistoryRepository assignmentHistoryRepository;

    private final AssignmentHistoryMapper assignmentHistoryMapper;

    @Override
    protected BaseRepository<AssignmentHistory> getBaseRepository() {
        return assignmentHistoryRepository;
    }

    @Override
    protected BaseMapper<AssignmentHistory, AssignmentHistoryDto> getBaseMapper() {
        return assignmentHistoryMapper;
    }

    @Override
    protected Page<AssignmentHistoryDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<AssignmentHistoryDto> getListSearchResults(String keyword) {
        return null;
    }
}
