package com.programming.courseservice.core.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "lectures",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uq_lectures_name")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lecture extends BaseModel {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String url;
    @ManyToOne(targetEntity = Section.class)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_lectures_section"))
    private Section section;
}
