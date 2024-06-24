package com.programming.courseservice.domain.dto;

import lombok.Data;

@Data
public class NotificationDto {

    private String sender;

    private String recipient;

    private String content;

    private String link;

    private String isViewed;
}
