package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(
        name = "notification"
)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Notification extends BaseModel {

    private String sender;

    private String recipient;

    private String content;

    private String link;

    @Column(name = "is_viewed")
    private Boolean isViewed;
}
