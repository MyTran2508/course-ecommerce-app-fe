package com.programming.userservice.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class NotificationDto {

    private String username;

    private String content;

    private String link;

    private String isViewed;
}
