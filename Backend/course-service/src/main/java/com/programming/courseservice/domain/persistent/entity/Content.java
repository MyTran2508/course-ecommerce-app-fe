package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "contents"
)
public class Content extends BaseModel {
    @OneToMany(mappedBy = "content", fetch = FetchType.EAGER)
    private Set<Section> sections;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "description_id", foreignKey = @ForeignKey(name = "fk_contents_description"))
    private Description description = new Description();
    @OneToOne(mappedBy = "content", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Course course;
}
