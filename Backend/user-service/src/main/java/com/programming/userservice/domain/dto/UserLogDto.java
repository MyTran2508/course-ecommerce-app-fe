package com.programming.userservice.domain.dto;

import com.programming.userservice.domain.persistent.enumrate.ActionName;
import com.programming.userservice.domain.persistent.enumrate.ActionObject;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
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
