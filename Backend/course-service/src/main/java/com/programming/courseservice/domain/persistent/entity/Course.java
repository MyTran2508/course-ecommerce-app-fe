package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(
        name = "courses",
        indexes = {
                @Index(columnList = "user_id", name = "idx_courses_user_id")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course extends BaseModel {
    @ManyToOne(targetEntity = Level.class)
    @JoinColumn(name = "level_id", foreignKey = @ForeignKey(name = "fk_courses_level"))
    private Level level;
    @ManyToOne(targetEntity = Language.class)
    @JoinColumn(name = "language_id", foreignKey = @ForeignKey(name = "fk_courses_language"))
    private Language language;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id", nullable = false, foreignKey = @ForeignKey(name = "fk_courses_content"))
    private Content content;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
    private Set<Image> images;
    @ManyToOne(targetEntity = Topic.class)
    @JoinColumn(name = "topic_id", foreignKey = @ForeignKey(name = "fk_course_topic"))
    private Topic topic;
    @Column(name = "user_id")
    private String userId;
}
