package com.programming.userservice.domain.persistent.entity;

import com.programming.userservice.domain.persistent.enumrate.ActionName;
import com.programming.userservice.domain.persistent.enumrate.ActionObject;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Table(
        name = "user_logs"
)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLog {
    @Id
    private String id;

    @Column(name = "user_name", length = 64)
    private String userName;

    @Column(name = "action_object", length = 32)
    @Enumerated(EnumType.STRING)
    private ActionObject actionObject;

    @Column(name = "action_name", length = 32)
    @Enumerated(EnumType.STRING)
    private ActionName actionName;

    @Column(name = "action_key", length = 64)
    private String actionKey;

    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @Column(length = 32)
    private String ip;

    @Column(name = "created")
    private Long created;

    @PrePersist
    protected void ensureId() {
        this.setId(UUID.randomUUID().toString());
        this.created = System.currentTimeMillis();
    }
}
