package com.programming.userservice.domain.persistent.entity;

import com.main.progamming.common.util.SystemUtil;
import com.programming.userservice.domain.persistent.enumrate.ActionName;
import jakarta.persistence.*;

import java.util.UUID;

@Table(
        name = "user_logs"
)
@Entity
public class UserLog {
    @Id
    private String id;

    @Column(name = "user_name", length = 64)
    private String userName;

    @Column(name = "action_object", length = 32)
    private String actionObject;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_name", length = 32)
    private ActionName actionName;

    @Column(length = 512)
    private String description;

    @Column(length = 32)
    private String ip;

    @Column(name = "created")
    private Long created;

    @PrePersist
    protected void ensureId() {
        this.created = System.currentTimeMillis();
    }
}
