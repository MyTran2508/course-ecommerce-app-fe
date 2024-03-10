package com.programming.courseservice.domain.dto;

import com.programming.courseservice.domain.persistent.entity.CommentReply;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForumLectureDto {

    private String id;

    private String lectureId;

    private String userId;

    private String userName;

    private String avatarUrl;

    private String comment;

    private List<CommentReplyDto> commentReplies;

    private Long created;

    private Long updated;
}
