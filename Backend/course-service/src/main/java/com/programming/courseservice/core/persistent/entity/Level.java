package com.programming.courseservice.core.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "levels",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uq_levels_name")
        },
        indexes = {
                @Index(columnList = "name", name = "idx_levels_name")
        }
)
public class Level extends BaseModel {
    @Column(length = 32, nullable = false)
    private String name;
    @Column(length = 512)
    private String description;
}
