package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "course",
        indexes = {
                @Index(columnList = "author_name", name = "idx_courses_author_name")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uq_course_name")
        }
)
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Course extends BaseModel {
    private String name;

    @Column(name = "sub_title")
    private String subTitle;

    private Double price;

    @ManyToOne(targetEntity = Level.class)
    @JoinColumn(name = "level_id", foreignKey = @ForeignKey(name = "fk_courses_level"))
    private Level level;

    @ManyToOne(targetEntity = Language.class)
    @JoinColumn(name = "language_id", foreignKey = @ForeignKey(name = "fk_courses_language"))
    private Language language;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "course")
    private Content content;

    @Column(name = "url_course_images", length = 512)
    private String urlCourseImages;

    @Column(name = "url_promotion_videos", length = 512)
    private String urlPromotionVideos;

    @ManyToOne(targetEntity = Topic.class)
    @JoinColumn(name = "topic_id", foreignKey = @ForeignKey(name = "fk_course_topic"))
    private Topic topic;

    @Column(name = "author_name")
    private String authorName;

    @Column(columnDefinition="tinyint(1) default 0")
    private boolean isApproved;
}
