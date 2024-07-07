package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.main.progamming.common.util.ExcludeFromComparisonField;
import com.programming.courseservice.domain.persistent.enumrate.LectureType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(
        name = "lecture"
)
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Lecture extends BaseModel implements Serializable {

    @Serial
    @ExcludeFromComparisonField
    private static final long serialVersionUID = 1L;

    @Column(name = "ordinal_number")
    private Integer ordinalNumber;

    private String name;

    private String url;

    private String fileName;

    @Column(length = 5000)
    private String description;

    @Column(name = "video_duration")
    private Long videoDuration;

    private LectureType lectureType;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ex_quiz_id", foreignKey = @ForeignKey(name = "fk_lecture_ex_quiz_id"))
    @ExcludeFromComparisonField
    private ExQuiz exQuiz;

    @OneToOne(mappedBy = "lecture", fetch = FetchType.EAGER)
    @ExcludeFromComparisonField
    private Assignment assignment;
}
