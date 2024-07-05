package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.courseservice.utilities.annotation.ExcludeFromComparisonField;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(
        name = "section"
)
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Section extends BaseModel implements Serializable {

    @Serial
    @ExcludeFromComparisonField
    private static final long serialVersionUID = 1L;

    @Column(name = "ordinal_number")
    private Integer ordinalNumber;

    @Column(nullable = false)
    private String name;

    @OneToMany(targetEntity = Lecture.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_lecture_section"))
    @OrderBy("ordinalNumber ASC")
    private List<Lecture> lectures;

    @ManyToOne
    @JoinColumn(name = "content_id", foreignKey = @ForeignKey(name = "fk_section_content"))
    @ToString.Exclude
    private Content content;

    private Long totalDurationVideoLectures;
}
