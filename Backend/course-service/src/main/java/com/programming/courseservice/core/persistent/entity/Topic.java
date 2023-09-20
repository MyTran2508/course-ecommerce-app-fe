package com.programming.courseservice.core.persistent.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
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
public class Topic extends BaseModel {
    @Column(nullable = false, length = 256)
    private String name;
    @Column(length = 512)
    private String description;
    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_topics_categories"))
    private Category category;
    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<Course> courses;
    @Override
    public String toString() {
        return "Topic{" +
                "name='" + name + '\'' +
                ", description=" + description + '}'
        + super.toString();
    }
    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
