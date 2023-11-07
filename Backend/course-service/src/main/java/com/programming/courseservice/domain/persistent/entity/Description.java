package com.programming.courseservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "description"
)
public class Description extends BaseModel {
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
