package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.courseservice.domain.persistent.enumrate.LevelName;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "level",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uq_levels_name")
        },
        indexes = {
                @Index(columnList = "name", name = "idx_levels_name")
        }
)
public class Level extends BaseModel {
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private LevelName name;
    @Column(length = 512)
    private String description;
}
