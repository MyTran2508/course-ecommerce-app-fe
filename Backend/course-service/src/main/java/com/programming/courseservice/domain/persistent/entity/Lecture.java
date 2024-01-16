package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
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

    @OneToMany(mappedBy = "lecture", fetch = FetchType.LAZY)
    private List<ExQuiz> exQuizList;
}
