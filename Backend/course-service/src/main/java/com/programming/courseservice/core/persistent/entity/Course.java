package com.programming.courseservice.core.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Set;

@Entity
@Table(
        name = "courses",
        indexes = {
                @Index(columnList = "user_id", name = "idx_contents_user_id")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course extends BaseModel {
    @OneToMany(mappedBy = "content", fetch = FetchType.EAGER)
    private Set<Section> setions;
    @ManyToOne(targetEntity = Level.class)
    @JoinColumn(name = "level_id", foreignKey = @ForeignKey(name = "fk_contents_level"))
    private Level level;
    @ManyToOne(targetEntity = Language.class)
    @JoinColumn(name = "language_id", foreignKey = @ForeignKey(name = "fk_contents_language"))
    private Language language;
}
