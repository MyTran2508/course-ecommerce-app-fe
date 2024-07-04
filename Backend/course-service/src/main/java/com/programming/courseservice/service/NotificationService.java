package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.error.exception.DataNotFoundException;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.NotificationDto;
import com.programming.courseservice.domain.mapper.NotificationMapper;
import com.programming.courseservice.domain.persistent.entity.Notification;
import com.programming.courseservice.repository.NotificationRepository;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService extends BaseServiceImpl<Notification, NotificationDto> {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    @Override
    protected BaseRepository<Notification> getBaseRepository() {
        return notificationRepository;
    }

    @Override
    protected BaseMapper<Notification, NotificationDto> getBaseMapper() {
        return notificationMapper;
    }

    @Override
    public DataResponse<String> create(NotificationDto dto) {
        return super.create(dto);
    }

    @Override
    public DataResponse<NotificationDto> update(String id, NotificationDto dto) {
        return super.update(id, dto);
    }

    @Override
    protected Page<NotificationDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {

        String username = searchKeywordDto.getKeyword().get(0) == null ? null : searchKeywordDto.getKeyword().get(0).trim();

        Page<Notification> notificationPage = notificationRepository.getByUsername(username, pageable);

        return notificationPage.map(notificationMapper::entityToDto);
    }

    @Override
    protected List<NotificationDto> getListSearchResults(String keyword) {
        return null;
    }

    public DataResponse<List<NotificationDto>> addList(List<NotificationDto> notificationDtoList) {

        List<Notification> notificationList = new ArrayList<>();

        for (NotificationDto notificationDto : notificationDtoList) {
            Notification notification = notificationMapper.dtoToEntity(notificationDto);
            notificationList.add(notification);
        }

        List<Notification> savedNotification = notificationRepository.saveAll(notificationList);

        List<NotificationDto> resultList = savedNotification.stream().map(notificationMapper::entityToDto).toList();

        return ResponseMapper.toDataResponseSuccess(resultList);
    }

    public DataResponse<String> setIsViewed(String id) {
        Notification notification = notificationRepository.findById(id).orElse(null);

        if (notification == null) {
            throw new DataNotFoundException(CourseConstrant.ErrorConstrant.ID_NOT_FOUND);
        }

        notification.setIsViewed(true);

        notificationRepository.save(notification);

        return ResponseMapper.toDataResponseSuccess(CourseConstrant.SuccessConstrant.UPDATE_SUCCESS);
    }
}
