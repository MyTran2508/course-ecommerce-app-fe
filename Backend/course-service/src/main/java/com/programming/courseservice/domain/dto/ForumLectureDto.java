package com.programming.courseservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ForumLectureDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String lectureId;

    private String userId;

    private String userName;

    private String avatarUrl;

    private String comment;

    private Long created;

    private Long updated;
}
