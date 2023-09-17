package com.programming.courseservice.core.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "contents"
)
public class Content extends BaseModel {
    @OneToMany(mappedBy = "content", fetch = FetchType.EAGER)
    private Set<Section> sections;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "document_id", nullable = false, foreignKey = @ForeignKey(name = "fk_contents_description"))
    private Description description;
}
