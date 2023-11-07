package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

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
@ToString(callSuper = true)
public class Language extends BaseModel {
    @Column(length = 32, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "language", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Course> courses;
}
