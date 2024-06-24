package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "assignment"
)
public class Assignment extends BaseModel {

    @Column(name = "questions", length = 3000)
    private String questions;

    @Column(name = "url_video_instructions")
    private String urlVideoInstructions;

    @Column(name = "text_instructions", length = 3000)
    private String textInstructions;

    @Column(name = "url_file_resource")
    private String urlFileResource;

    @Column(name = "estimated_duration")
    private Long estimatedDuration;

    @Column(name = "url_video_solution")
    private String urlVideoSolution;

    @Column(name = "url_file_solution")
    private String urlFileSolution;

    @Column(name = "text_solution", length = 3000)
    private String textSolution;

    @OneToOne(targetEntity = Lecture.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", foreignKey = @ForeignKey(name = "fk_assignment_lecture_id"))
    private Lecture lecture;
}
