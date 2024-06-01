package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "assignment_history"
)
public class AssignmentHistory extends BaseModel {

    @Column(name = "text_answer", length = 5000)
    private String textAnswer;

    @Column(name = "url_video_answer", length = 400)
    private String urlFileAnswer;

    @Column(name = "time_submit")
    private Long timeSubmit;

    private Float score;

    private String evaluation;
}
