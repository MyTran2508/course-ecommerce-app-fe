package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import com.programming.courseservice.utilities.annotation.ExcludeFromComparisonField;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "description"
)
public class Description extends BaseModel implements Serializable {

    @Serial
    @ExcludeFromComparisonField
    private static final long serialVersionUID = 1L;

    @Column(length = 1000)
    private String requirements;

    @Column(length = 5000)
    private String details;

    @Column(name = "target_consumers", length = 1000)
    private String targetConsumers;

    @OneToOne(mappedBy = "description")
    @ToString.Exclude
    private Content content;
}
