package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "course_review")
public class CourseReview extends BaseModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(targetEntity = Course.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_course_review_course"))
    private Course course;

    private String message;

    private Float rating;

    private String username;

    @Column(name = "like_amount")
    private Integer likeAmount;

    @Column(name = "dislike_amount")
    private Integer disLikeAmount;

    @Column(name = "user_likes")
    private String userLikes;

    @Column(name = "user_dislikes")
    private String userDislikes;

    @Override
    protected void ensureId() {
        this.likeAmount = 0;
        this.disLikeAmount = 0;
        this.userLikes = "";
        this.userDislikes = "";
        super.ensureId();
    }
}
