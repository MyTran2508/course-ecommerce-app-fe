package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "language",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uq_languages_name")
        },
        indexes = {
                @Index(columnList = "name", name = "idx_languages_name")
        }
)
@ToString(callSuper = true)
public class Language extends BaseModel {
    @Column(length = 32, nullable = false)
    private String name;
}
