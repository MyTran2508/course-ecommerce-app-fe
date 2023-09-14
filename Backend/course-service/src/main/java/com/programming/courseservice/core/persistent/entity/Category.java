package com.programming.courseservice.core.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}, name = "uq_categories_name")
        },
        indexes = {
                @Index(columnList = "name", name = "idx_categories_name")
        }
)
public class Category extends BaseModel {
    @Column(nullable = false, length = 64)
    private String name;
    @Column(length = 512)
    private String description;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    Set<Topic> topics = new HashSet<>();
}
