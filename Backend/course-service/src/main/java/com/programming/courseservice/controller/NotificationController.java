package com.programming.courseservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.courseservice.domain.dto.NotificationDto;
import com.programming.courseservice.domain.persistent.entity.Notification;
import com.programming.courseservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/notification")
public class NotificationController extends BaseApiImpl<Notification, NotificationDto> {

    private final NotificationService notificationService;

    @Override
    protected BaseService<Notification, NotificationDto> getBaseService() {
        return notificationService;
    }

    @Override
    public DataResponse<String> add(NotificationDto objectDTO) {
        return super.add(objectDTO);
    }

    @PostMapping("/add-list")
    public DataResponse<List<NotificationDto>> addList(@RequestBody List<NotificationDto> notificationDtoList) {
        return notificationService.addList(notificationDtoList);
    }

    @Override
    public DataResponse<NotificationDto> update(NotificationDto objectDTO, String id) {
        return super.update(objectDTO, id);
    }

    @Override
    public ListResponse<NotificationDto> searchByKeyword(SearchKeywordDto searchKeywordDto) {
        return super.searchByKeyword(searchKeywordDto);
    }
}
