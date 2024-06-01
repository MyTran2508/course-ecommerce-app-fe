package com.programming.userservice.controller;

import com.main.progamming.common.controller.BaseApiImpl;
import com.main.progamming.common.dto.SearchKeywordDto;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ListResponse;
import com.main.progamming.common.service.BaseService;
import com.programming.userservice.domain.dto.NotificationDto;
import com.programming.userservice.domain.persistent.entity.Notification;
import com.programming.userservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        System.out.println("Vao day");
        return super.add(objectDTO);
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
