package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.main.progamming.common.util.ExcludeFromComparisonField;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@DynamicUpdate
@SelectBeforeUpdate
public class Course extends BaseModel implements Serializable {

    @Serial
    @ExcludeFromComparisonField
    private static final long serialVersionUID = 1L;

    private String name;

    @Column(name = "sub_title")
    private String subTitle;

    private Double price;

    @ManyToOne(targetEntity = Level.class)
    @JoinColumn(name = "level_id", foreignKey = @ForeignKey(name = "fk_courses_level"))
    @ExcludeFromComparisonField
    private Level level;

    @ManyToOne(targetEntity = Language.class)
    @JoinColumn(name = "language_id", foreignKey = @ForeignKey(name = "fk_courses_language"))
    @ExcludeFromComparisonField
    private Language language;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "course")
    @ExcludeFromComparisonField
    private Content content;

    @Column(name = "url_course_images", length = 512)
    private String urlCourseImages;

    @Column(name = "url_promotion_videos", length = 512)
    private String urlPromotionVideos;

    @ManyToOne(targetEntity = Topic.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "topic_id", foreignKey = @ForeignKey(name = "fk_course_topic"))
    private Topic topic;

    @Column(name = "author_name")
    private String authorName;

    @Column(columnDefinition="tinyint(1) default 0", name = "is_approved")
    @ExcludeFromComparisonField
    private Boolean isApproved;

    @Column(columnDefinition="tinyint(1) default 0", name = "is_completed_content")
    private Boolean isCompletedContent;

    @Column(columnDefinition = "tinyint(1) default 0", name = "is_awaiting_approval")
    @ExcludeFromComparisonField
    private Boolean isAwaitingApproval;

    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("created DESC")
    @ExcludeFromComparisonField
    private List<CourseIssueReport> courseIssueReports;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @ExcludeFromComparisonField
    private List<CourseReview> courseReviews;

    @Column(name = "average_rating")
    @ExcludeFromComparisonField
    private Float averageRating;

    @Column(name = "total_ratings")
    @ExcludeFromComparisonField
    private Integer totalRatings;

    @Override
    protected void ensureId() {
        this.isApproved = false;
        this.isCompletedContent = false;
        this.isAwaitingApproval = false;
        super.ensureId();
    }
}
