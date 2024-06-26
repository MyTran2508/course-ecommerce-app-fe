package com.programming.courseservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.programming.courseservice.domain.persistent.entity.AssignmentHistory;
import com.programming.courseservice.domain.persistent.entity.Lecture;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class AssignmentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String questions;

    private String urlVideoInstructions;

    private String textInstructions;

    private String urlFileResource;

    private Long estimatedDuration;

    private String urlVideoSolution;

    private String urlFileSolution;

    @JsonBackReference
    private LectureDto lecture;
}
