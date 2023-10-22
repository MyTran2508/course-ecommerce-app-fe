package com.programming.courseservice.domain.persistent.entity;
import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(
        name = "sections"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Section extends BaseModel {
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER)
    private Set<Lecture> lectures;
    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER)
    private Set<Document> documents;
    @ManyToOne(targetEntity = Content.class)
    @JoinColumn(name = "content_id", foreignKey = @ForeignKey(name = "fk_sections_content"))
    private Content content;
}
