package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "category",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"}, name = "uq_categories_name")
        },
        indexes = {
                @Index(columnList = "name", name = "idx_categories_name")
        }
)
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Category extends BaseModel {

    @Column(nullable = false, length = 64)
    private String name;

    @Column(length = 512)
    private String description;

    @OneToMany(targetEntity = Topic.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_topic_category"))
    private List<Topic> topics;

//    public void setTopicsAll(List<Topic> newTopics) {
//        this.topics.clear();
//
//        if(newTopics != null) {
//            this.topics.addAll(newTopics);
//        }
//    }
}
