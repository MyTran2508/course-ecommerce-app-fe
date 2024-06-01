package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.AvatarDto;
import com.programming.courseservice.domain.dto.ForumLectureDto;
import com.programming.courseservice.domain.mapper.ForumLectureMapper;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import com.programming.courseservice.repository.ForumLectureRepository;
import com.programming.courseservice.utilities.communication.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumLectureService extends BaseServiceImpl<ForumLecture, ForumLectureDto> {

    private final ForumLectureRepository forumLectureRepository;

    private final ForumLectureMapper forumLectureMapper;

    private final UserApi userApi;

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

        Page<ForumLecture> forumLecturePage = forumLectureRepository.findByLectureId(lectureId, pageable);

        Page<ForumLectureDto> forumLectureDtoPage = forumLecturePage.map(forumLecture -> {
            ForumLectureDto forumLectureDto = forumLectureMapper.entityToDto(forumLecture);

            // get user avatar from user service
            ResponseEntity<AvatarDto> responseAvatar = userApi.getAvatar(forumLecture.getUserName());
            AvatarDto avatarDto = responseAvatar.getBody();

            String rawResponse = avatarDto.toString();
            String rawAvatar = null;

            if (!rawResponse.contains("statusCode")) {
                rawAvatar = avatarDto.getRawAvatar();
            }

            // set user avatar
            forumLectureDto.setRawAvatar(rawAvatar);
            return forumLectureDto;
        });

        return forumLectureDtoPage;
    }

    @Override
    protected List<ForumLectureDto> getListSearchResults(String keyword) {
        return null;
    }

    @Override
    public DataResponse<String> create(ForumLectureDto dto) {
        return super.create(dto);
    }

    @Override
    public DataResponse<ForumLectureDto> update(String id, ForumLectureDto dto) {
        return super.update(id, dto);
    }
}
