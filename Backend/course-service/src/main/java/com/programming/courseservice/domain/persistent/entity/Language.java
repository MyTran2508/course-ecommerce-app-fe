package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.main.progamming.common.util.ExcludeFromComparisonField;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

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
public class Language extends BaseModel implements Serializable {

    @Serial
    @ExcludeFromComparisonField
    private static final long serialVersionUID = 1L;

    @Column(length = 32, nullable = false)
    private String name;
}
