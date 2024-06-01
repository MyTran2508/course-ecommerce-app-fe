package com.programming.userservice.domain.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class NotificationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userName;

    private String content;

    private String link;

    private String isViewed;
}
