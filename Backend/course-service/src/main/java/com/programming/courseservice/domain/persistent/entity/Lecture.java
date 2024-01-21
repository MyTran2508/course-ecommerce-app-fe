package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.courseservice.domain.persistent.enumrate.LectureType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(
        name = "lecture"
)
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Lecture extends BaseModel {
    @Column(name = "ordinal_number")
    private Integer ordinalNumber;

    private String name;

    private String url;

    private String fileName;

    @Column(name = "video_duration")
    private Long videoDuration;

    private LectureType lectureType;

    @OneToOne(targetEntity = ExQuiz.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ex_quiz_id", foreignKey = @ForeignKey(name = "fk_lecture_ex_quiz_id"))
    private ExQuiz exQuiz;
}
