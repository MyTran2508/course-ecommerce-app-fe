package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "comment_reply"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentReply extends BaseModel {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(length = 2000)
    private String comment;

    @ManyToOne(targetEntity = ForumLecture.class)
    @JoinColumn(name = "forum_lecture_id", foreignKey = @ForeignKey(name = "fk_comment_reply_forum_lecture_id"))
    private ForumLecture forumLecture;
}
