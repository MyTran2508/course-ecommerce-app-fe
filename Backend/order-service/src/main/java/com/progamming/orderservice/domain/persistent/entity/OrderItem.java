package com.progamming.orderservice.domain.persistent.entity;

import com.main.progamming.common.model.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true)
@Table(
        name = "order_items"
)
public class OrderItem extends BaseModel {
    @Column(name = "course_id")
    private String courseId;
    private Integer quantity;
    private Double price;
}
