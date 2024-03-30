package com.programming.courseservice.domain.dto;

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
public class CommentReplyDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String userName;

    private String avatarUrl;

    private String comment;

    private Long created;

    private Long updated;
}
