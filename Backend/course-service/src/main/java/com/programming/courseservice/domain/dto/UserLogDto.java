package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.enumrate.ActionName;
import com.programming.courseservice.domain.persistent.enumrate.ActionObject;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Data
@ToString
@Builder
public class UserLogDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String userName;

    @Enumerated(EnumType.STRING)
    private ActionObject actionObject;

    @Enumerated(EnumType.STRING)
    private ActionName actionName;

    private String actionKey;

    private String description;

    private String ip;

    private Long created;
}
