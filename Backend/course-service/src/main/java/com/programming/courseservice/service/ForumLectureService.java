package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.main.progamming.common.util.SystemUtil;
import com.programming.courseservice.domain.dto.AvatarDto;
import com.programming.courseservice.domain.dto.ForumLectureDto;
import com.programming.courseservice.domain.mapper.ForumLectureMapper;
import com.programming.courseservice.domain.persistent.entity.ForumLecture;
import com.programming.courseservice.repository.ForumLectureRepository;
import com.programming.courseservice.utilities.communication.UserApi;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
            String username = SystemUtil.getCurrentUsername();
            String[] userLikeArr = forumLecture.getUserLikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
            Boolean isUserLiking = Arrays.stream(userLikeArr)
                    .anyMatch(userLike -> Objects.equals(userLike, username));

            String[] userDisLikeArr = forumLecture.getUserDislikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
            Boolean isUserDisLiking = Arrays.stream(userDisLikeArr)
                    .anyMatch(userLike -> Objects.equals(userLike, username));

            forumLectureDto.setIsUserLike(isUserLiking);
            forumLectureDto.setIsUserDislike(isUserDisLiking);
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

    @Transactional
    @SuppressWarnings("unchecked")
    public DataResponse<ForumLectureDto> setLike(String id, String userName, Boolean isCancel) {

        ForumLecture forumLecture = forumLectureRepository.findById(id).get();
        Integer likeAmount = forumLecture.getLikeAmount();
        String userLikes = forumLecture.getUserLikes();
        String[] userLikeArr = forumLecture.getUserLikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
        Boolean isUserLiking = Arrays.stream(userLikeArr)
                .anyMatch(userLike -> Objects.equals(userLike, userName));

        String userDislikes = forumLecture.getUserDislikes();
        Integer disLikeAmount = forumLecture.getDisLikeAmount();
        String[] userDislikeArr = forumLecture.getUserDislikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
        Boolean isUserDisliking = Arrays.stream(userDislikeArr)
                .anyMatch(userDislike -> Objects.equals(userDislike, userName));

        // has already like and cancel like
        if (isCancel && isUserLiking) {
            userLikes = userLikes.replace(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE, "")
                    .replace(userName, "");
            likeAmount--;
        }
        // not like and like
        if (!isCancel && !isUserLiking) {
            userLikes = userLikes.concat(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
            likeAmount++;

            if (isUserDisliking) {
                userDislikes = userDislikes.replace(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE, "")
                        .replace(userName, "");
                disLikeAmount--;
                forumLecture.setDisLikeAmount(disLikeAmount);
            }
        }
        forumLecture.setUserLikes(userLikes);
        forumLecture.setLikeAmount(likeAmount);
        forumLecture.setUserDislikes(userDislikes);

        forumLectureRepository.save(forumLecture);
        ForumLectureDto result = forumLectureMapper.entityToDto(forumLecture);
        if (isCancel) {
            result.setIsUserLike(false);
        } else {
            result.setIsUserLike(true);
        }
        return ResponseMapper.toDataResponseSuccess(result);
    }

    // set dislike review
    @Transactional
    @SuppressWarnings("unchecked")
    public DataResponse<ForumLectureDto> setDislike(String id, String userName, Boolean isCancel) {
        ForumLecture forumLecture = forumLectureRepository.findById(id).get();

        Integer likeAmount = forumLecture.getLikeAmount();
        String userLikes = forumLecture.getUserLikes();
        String[] userLikeArr = forumLecture.getUserLikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
        Boolean isUserLiking = Arrays.stream(userLikeArr)
                .anyMatch(userLike -> Objects.equals(userLike, userName));

        Integer disLikeAmount = forumLecture.getDisLikeAmount();
        String userDislikes = forumLecture.getUserDislikes();
        String[] userDislikeArr = forumLecture.getUserDislikes().split(CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
        Boolean isUserDisliking = Arrays.stream(userDislikeArr)
                .anyMatch(userDislike -> Objects.equals(userDislike, userName));

        // has already dislike and cancel dislike
        if (isCancel && isUserDisliking) {
            userDislikes = userDislikes.replace(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE, "")
                    .replace(userName, "");
            disLikeAmount--;
        }
        // not dislike and dislike
        if (!isCancel && !isUserDisliking) {
            userDislikes = userDislikes.concat(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE);
            disLikeAmount++;

            if (isUserLiking) {
                userLikes = userLikes.replace(userName + CourseConstrant.RegexConstrant.USERNAME_LIKE_REVIEW_SEPERATE, "")
                        .replace(userDislikes, "");
                likeAmount--;
                forumLecture.setLikeAmount(likeAmount);
            }
        }
        forumLecture.setUserLikes(userLikes);
        forumLecture.setUserDislikes(userDislikes);
        forumLecture.setDisLikeAmount(disLikeAmount);

        forumLectureRepository.save(forumLecture);
        ForumLectureDto result = forumLectureMapper.entityToDto(forumLecture);
        if (isCancel) {
            result.setIsUserDislike(false);
        } else {
            result.setIsUserDislike(true);
        }
        return ResponseMapper.toDataResponseSuccess(result);
    }
}
