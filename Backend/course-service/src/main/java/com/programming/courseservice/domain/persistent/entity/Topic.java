package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "topic",
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

    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Course> courses;

    public Topic(String name, String description) {
        this.name = name;
        this.description = description;
    }

//    @Override
//    public boolean equals(Object o) {
//        if(this == o) return true;
//        if(o == null || getClass() != o.getClass()) return false;
//        Topic user = (Topic) o;
//
//        return Objects.equals(this.getId(), user.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return this.getId() != null ? getId().hashCode() : 0;
//    }
}
