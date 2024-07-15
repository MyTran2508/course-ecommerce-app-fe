package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.main.progamming.common.util.ExcludeFromComparisonField;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(
        name = "forum_lecture"
)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForumLecture extends BaseModel {

    @Column(name = "lecture_id")
    private String lectureId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(length = 2000)
    private String comment;

    @Column(name = "count_reply_comment")
    private Integer countReplyComment;

    @Column(name = "like_amount")
    private Integer likeAmount;

    @Column(name = "dislike_amount")
    private Integer disLikeAmount;

    @Column(name = "user_likes")
    private String userLikes;

    @Column(name = "user_dislikes")
    private String userDislikes;

    @OneToMany(mappedBy = "forumLecture", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderBy("created ASC")
    private List<CommentReply> commentReplies;

    @Override
    protected void ensureId() {
        this.countReplyComment = 0;
        this.likeAmount = 0;
        this.disLikeAmount = 0;
        this.userLikes = "";
        this.userDislikes = "";
        super.ensureId();
    }
}
