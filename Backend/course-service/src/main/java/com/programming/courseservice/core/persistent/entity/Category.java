package com.programming.courseservice.core.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@Getter
@Setter
@Builder
@DynamicUpdate
@DynamicInsert
@ToString
public class Category extends BaseModel {
    @Column(nullable = false, length = 64)
    private String name;
    @Column(length = 512)
    private String description;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics = new ArrayList<>();
    @Override
    protected void ensureId() {
        for (Topic topic: topics) {
            topic.setCategory(this);
        }
        super.ensureId();
    }

    @Override
    protected void setUpdated() {
        for (Topic topic: topics) {
            topic.setCategory(this);
        }
        super.setUpdated();
    }
    public void setNewTopics(List<Topic> topics) {
        this.topics.clear();
        if(topics != null) {
            this.topics.addAll(topics);
        }
    }
}
