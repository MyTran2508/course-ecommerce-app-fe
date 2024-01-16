package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(
        name = "user_wrong_answer"
)
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWrongAnswer {
    @Id
    private String id;

    private Integer wrongAnswer;

    private Integer userAnswer;

    @PrePersist
    private void ensureId() {
        this.id = UUID.randomUUID().toString();
    }
}
