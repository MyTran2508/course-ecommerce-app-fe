package com.programming.courseservice.controller;

import com.main.progamming.common.response.DataResponse;
import com.programming.courseservice.domain.dto.CommentReplyDto;
import com.programming.courseservice.domain.dto.NotificationDto;
import com.programming.courseservice.domain.mapper.NotificationMapper;
import com.programming.courseservice.repository.NotificationRepository;
import com.programming.courseservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NotificationRealtimeController {

    private final NotificationService notificationService;

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    private final SimpMessagingTemplate simpMessagingTemplate;

//    @MessageMapping("/notification/add")
//    public void add(
//            @Payload NotificationDto notificationDto
//    ) {
//        DataResponse<String> response = notificationService.create()
//    }
}
