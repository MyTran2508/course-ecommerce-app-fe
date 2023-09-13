package com.programming.courseservice.entity;

import com.main.ocean.common.model.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "category"
)
@DynamicInsert
@DynamicUpdate
public class Category extends BaseModel {
    @Column(nullable = false, length = 64)
    private String categoryName;
    @Column(length = 512)
    private String description;
}
