package com.programming.courseservice.core.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "overviews"
)
public class Overview extends BaseModel {
    private Double hourseOfVideo;
    private Integer codingExercises;
    private Integer amountArticles;
    private Integer downloadableResources;
    private boolean accessOnMobileAndTV;
}
