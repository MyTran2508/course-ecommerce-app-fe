package com.programming.courseservice.core.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CollectionIdJdbcTypeCode;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "images",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = "uq_levels_name")
        },
        indexes = {
                @Index(columnList = "name", name = "idx_levels_name")
        }
)
public class Image extends BaseModel {
    @Column(nullable = false)
    private String url;
    @Column(name = "is_default_image", columnDefinition = "bit default 0 not null")
    private boolean isDefaultImage;
}
