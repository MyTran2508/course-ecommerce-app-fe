package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "content"
)
public class Content extends BaseModel {
    @OneToMany(mappedBy = "content", fetch = FetchType.EAGER)
    @OrderBy("ordinalNumber ASC")
    private List<Section> sections;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "description_id", foreignKey = @ForeignKey(name = "fk_content_description"))
    private Description description;

    @OneToOne
    @JoinColumn(name = "course_id", foreignKey = @ForeignKey(name = "fk_content_course"))
    @ToString.Exclude
    private Course course;
}
