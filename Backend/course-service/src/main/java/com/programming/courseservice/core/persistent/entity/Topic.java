package com.programming.courseservice.core.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "topics",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}, name = "uq_topics_name")
        },
        indexes = {
                @Index(columnList = "name", name = "idx_topics_name"),
                @Index(columnList = "category_id", name = "idx_topics_category_id")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic extends BaseModel {
    @Column(nullable = false, length = 256)
    private String name;
    @Column(length = 512)
    private String description;
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_topics_categories"))
    private Category category;
}
