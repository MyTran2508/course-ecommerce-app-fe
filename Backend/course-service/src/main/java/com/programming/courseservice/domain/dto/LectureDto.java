package com.programming.courseservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.programming.courseservice.domain.persistent.enumrate.LectureType;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LectureDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private Integer ordinalNumber;

    private String name;

    private String url;

    private String fileName;

    private Long videoDuration;

    private String description;

    private LectureType lectureType;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ExQuizDto exQuiz;

    @JsonManagedReference
    private AssignmentDto assignment;
}
