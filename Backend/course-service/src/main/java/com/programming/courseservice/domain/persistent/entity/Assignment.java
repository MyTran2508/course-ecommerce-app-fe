package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "assignment"
)
public class Assignment extends BaseModel {

    @Column(name = "question", length = 5000)
    private String question;

    @Column(name = "url_video_solution")
    private String urlVideoSolution;

    @Column(name = "url_file_solution")
    private String urlFileSolution;

    @Column(name = "estimated_duration")
    private Long estimatedDuration;

    @Column(name = "url_video_instructions")
    private String urlVideoInstructions;

    @Column(name = "text_instructions", length = 5000)
    private String textInstructions;

    @Column(name = "url_file_resource")
    private String urlFileResource;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "assignment_history", foreignKey = @ForeignKey(name = "fk_assignment_assignment_history"))
    private AssignmentHistory assignmentHistory;
}
