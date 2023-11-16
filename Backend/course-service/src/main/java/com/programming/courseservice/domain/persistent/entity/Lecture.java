package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String url;
    @Column(name = "video_duration")
    private Long videoDuration;
}
