package com.programming.courseservice.domain.persistent.entity;

import com.programming.courseservice.domain.persistent.enumrate.ActionName;
import com.programming.courseservice.domain.persistent.enumrate.ActionObject;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
public class UserLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String userName;

    private ActionObject actionObject;

    private ActionName actionName;

    private String actionKey;

    private String description;

    private String ip;

    private Long created;
}
