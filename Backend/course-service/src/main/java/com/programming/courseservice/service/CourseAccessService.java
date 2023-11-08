package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.CourseAccessDto;
import com.programming.courseservice.domain.mapper.CourseAccessMapper;
import com.programming.courseservice.domain.persistent.entity.CourseAccess;
import com.programming.courseservice.repository.CourseAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseAccessService extends BaseServiceImpl<CourseAccess, CourseAccessDto> {
    private final CourseAccessRepository coursePermissionRepository;
    private final CourseAccessMapper coursePermissionMapper;
    @Override
    protected BaseRepository<CourseAccess> getBaseRepository() {
        return coursePermissionRepository;
    }

    @Override
    protected BaseMapper<CourseAccess, CourseAccessDto> getBaseMapper() {
        return coursePermissionMapper;
    }

    @Override
    protected Page<CourseAccessDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {
        return null;
    }

    @Override
    protected List<CourseAccessDto> getListSearchResults(String keyword) {
        return null;
    }

    public DataResponse<Boolean> hasAccessToCourse(String userId, String courseId) {
        Optional<CourseAccess> optionalCourseAccess = coursePermissionRepository.findByUserIdAndCourseId(userId, courseId);
        if(optionalCourseAccess.isPresent()) {
            return ResponseMapper.toDataResponseSuccess(true);
        } else {
            return ResponseMapper.toDataResponseSuccess(false);
        }
    }
}
