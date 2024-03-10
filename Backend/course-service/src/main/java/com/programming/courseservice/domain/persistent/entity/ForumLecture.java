package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
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

    @OneToMany(targetEntity = CommentReply.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "forum_lecture_id", foreignKey = @ForeignKey(name = "fk_comment_reply_forum_lecture_id"))
    @OrderBy("created ASC")
    private List<CommentReply> commentReplies;
}
