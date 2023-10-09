package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "documents"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document extends BaseModel {
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String url;
    @ManyToOne(targetEntity = Section.class)
    @JoinColumn(name = "section_id", foreignKey = @ForeignKey(name = "fk_documents_section"))
    private Section section;
}
