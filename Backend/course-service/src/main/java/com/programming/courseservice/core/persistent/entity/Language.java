package com.programming.courseservice.core.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.prefs.BackingStoreException;

@Entity
@Data
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
public class Language extends BaseModel {
    @Column(length = 32, nullable = false)
    private String name;
}
