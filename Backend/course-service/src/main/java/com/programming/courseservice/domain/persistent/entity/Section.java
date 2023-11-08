package com.programming.courseservice.domain.persistent.entity;
import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "section"
)
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Section extends BaseModel {
    @Column(nullable = false)
    private String name;

    @OneToMany(targetEntity = Lecture.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_lecture_section"))
    private List<Lecture> lectures;

    @OneToMany(targetEntity = Document.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_document_section"))
    private List<Document> documents;

    @ManyToOne
    @JoinColumn(name = "content_id", foreignKey = @ForeignKey(name = "fk_section_content"))
    @ToString.Exclude
    private Content content;
}
