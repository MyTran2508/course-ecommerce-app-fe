package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "images"
)
public class Image extends BaseModel {
    @Column(nullable = false)
    private String url;
    @Column(name = "is_default_image", columnDefinition = "bit default 0 not null")
    private boolean isDefaultImage;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false, foreignKey = @ForeignKey(name = "fk_images_course"))
    private Course course;
}
