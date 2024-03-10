package com.programming.courseservice.domain.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentReplyDto {

    private String id;

    private String userId;

    private String userName;

    private String avatarUrl;

    private String comment;

    private Long created;

    private Long updated;
}
