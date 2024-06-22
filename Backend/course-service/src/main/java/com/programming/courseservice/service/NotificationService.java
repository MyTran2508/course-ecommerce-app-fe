package com.programming.courseservice.service;

import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.model.BaseMapper;
import com.main.progamming.common.repository.BaseRepository;
import com.main.progamming.common.service.BaseServiceImpl;
import com.programming.courseservice.domain.dto.NotificationDto;
import com.programming.courseservice.domain.mapper.NotificationMapper;
import com.programming.courseservice.domain.persistent.entity.Notification;
import com.programming.courseservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    protected Page<NotificationDto> getPageResults(SearchKeywordDto searchKeywordDto, Pageable pageable) {

        String username = searchKeywordDto.getKeyword().get(0) == null ? null : searchKeywordDto.getKeyword().get(0).trim();

        Page<Notification> notificationPage = notificationRepository.getByUsername(username, pageable);

        return notificationPage.map(notificationMapper::entityToDto);
    }

    @Override
    protected List<NotificationDto> getListSearchResults(String keyword) {
        return null;
    }
}
