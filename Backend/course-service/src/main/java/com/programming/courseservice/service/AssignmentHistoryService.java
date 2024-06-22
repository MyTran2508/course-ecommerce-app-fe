package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataConflictException;
import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.AssignmentHistoryDto;
import com.programming.courseservice.domain.mapper.AssignmentHistoryMapper;
import com.programming.courseservice.domain.persistent.entity.AssignmentHistory;
import com.programming.courseservice.repository.AssignmentHistoryRepository;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public DataResponse<String> create(AssignmentHistoryDto dto) {

        // check duplicate assignment history
        Integer maxOrginalNumber = assignmentHistoryRepository.getMaxOriginalNumber(
                dto.getUsername(), dto.getAssignment().getLecture().getId());

        // create assignment history
        AssignmentHistory assignmentHistory = assignmentHistoryMapper.dtoToEntity(dto);

        if (maxOrginalNumber != null) {
            Optional<AssignmentHistory> assignmentHistoryAlreadyExists = assignmentHistoryRepository.findByUsernameAndLectureIdAndOriginalNumber(
                    dto.getUsername(), dto.getAssignment().getLecture().getId(), maxOrginalNumber + 1);

            if (assignmentHistoryAlreadyExists.isPresent()) {
                throw new DataNotFoundException(CourseConstrant.ErrorConstrant.ASSIGNMENT_HISTORY_ALREADY_EXISTS);
            }

            // check if assignemnt is not yet completed
            AssignmentHistory savedAssignmentHistory = assignmentHistoryRepository.findByUsernameAndLectureIdAndOriginalNumber(
                    dto.getUsername(), dto.getAssignment().getLecture().getId(), maxOrginalNumber).get();
            if (savedAssignmentHistory.getScore() == null || savedAssignmentHistory.getEvaluation() == null) {
                throw new DataConflictException(CourseConstrant.ErrorConstrant.ASSIGNMENT_HISTORY_NOT_YET_COMPLETED);
            }

            assignmentHistory.setOriginalNumber(maxOrginalNumber + 1);
            assignmentHistoryRepository.save(assignmentHistory);
        } else {
            assignmentHistory.setOriginalNumber(0);
            assignmentHistoryRepository.save(assignmentHistory);
        }

        // return success response
        return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.INSERT_SUCCES);
    }

    public ListResponse<AssignmentHistoryDto> getByUsername(String username) {

        // get all assignment history by username
        List<AssignmentHistory> resultList = assignmentHistoryRepository.findByUsername(username);

        // convert to DTO
        return ResponseMapper.toListResponseSuccess(resultList.stream()
                .map(assignmentHistoryMapper::entityToDto)
                .toList());
    }

    public ListResponse<AssignmentHistoryDto> getByUsernameAndLectureId(String username, String lectureId) {

        // get all assignment history by username and lecture id
        List<AssignmentHistory> resultList = assignmentHistoryRepository.findByUsernameAndLectureId(username, lectureId);

        // convert to DTO
        return ResponseMapper.toListResponseSuccess(resultList.stream()
                .map(assignmentHistoryMapper::entityToDto)
                .toList());
    }
}
