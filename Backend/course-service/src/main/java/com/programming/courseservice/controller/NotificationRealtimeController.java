package com.programming.courseservice.controller;

import com.main.progamming.common.message.StatusCode;
import com.main.progamming.common.response.DataResponse;
import com.main.progamming.common.response.ResponseMapper;
import com.programming.courseservice.domain.dto.NotificationDto;
import com.programming.courseservice.domain.mapper.NotificationMapper;
import com.programming.courseservice.domain.persistent.entity.Notification;
import com.programming.courseservice.repository.NotificationRepository;
import com.programming.courseservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NotificationRealtimeController {

    private final NotificationService notificationService;

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/notification/add")
    public void add(
            @Payload NotificationDto notificationDto
    ) {
        DataResponse<String> response = notificationService.create(notificationDto);
        if (response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            String notificationId = response.getData().split("ID: ")[1];
            System.out.println(notificationId);
            Notification notification = notificationRepository.findById(notificationId).get();
            NotificationDto responseDto = notificationMapper.entityToDto(notification);
            simpMessagingTemplate.convertAndSend("/rt/response/courses/notification/" + notificationDto.getRecipient(),
                    ResponseMapper.toDataResponseSuccess(responseDto));
        } else {
            simpMessagingTemplate.convertAndSend("/rt/response/courses/notification/" + notificationDto.getRecipient(),
                    ResponseMapper.toDataResponseSuccess(null));
        }
    }

    @MessageMapping("/notification/add-list")
    public void addList(
            @Payload List<NotificationDto> notificationDtoList
    ) {
        DataResponse<List<NotificationDto>> response = notificationService.addList(notificationDtoList);
        if (response.getStatusCode() == StatusCode.REQUEST_SUCCESS) {
            List<NotificationDto> resultList = response.getData();
            for (NotificationDto notificationDto : resultList) {
                simpMessagingTemplate.convertAndSend("/rt/response/courses/notification/" + notificationDto.getRecipient(),
                        ResponseMapper.toDataResponseSuccess(notificationDto));
            }
        } else {
            simpMessagingTemplate.convertAndSend("/rt/response/courses/notification/",
                    ResponseMapper.toDataResponseSuccess(null));
        }
    }

    @MessageMapping("/notification/update")
    public void update(
            @Payload NotificationDto notificationDto
    ) {
        DataResponse<NotificationDto> responseDto = notificationService.update(notificationDto.getId(), notificationDto);
        simpMessagingTemplate.convertAndSend("/rt/response/courses/notification/" + notificationDto.getRecipient(), responseDto);
    }
}
